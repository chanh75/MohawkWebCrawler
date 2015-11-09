package com.mohawk.webcrawler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.verb.ElseIf_Verb;
import com.mohawk.webcrawler.lang.verb.Else_Verb;
import com.mohawk.webcrawler.lang.verb.If_Verb;
import com.mohawk.webcrawler.lang.verb.While_Verb;

public class ScriptCompiler {

	/**
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static LinkedList compile(String filename) throws IOException, Exception {
		
		List<String> lines = FileUtils.readLines(new File(filename), "UTF-8");
		StringBuffer fileContents = new StringBuffer();
		
		CommentRemover comment = new CommentRemover();
		
		for (String line : lines) {
			fileContents.append(comment.remove(line)).append(' ');
		}
		
		System.out.println("fileContents>> " + fileContents);
		
		// reorganize the tokens into scopes
		return compile0(fileContents.toString());
		
	}
	
	/**
	 * 
	 * @param programText
	 * @return
	 * @throws CompilationException
	 */
	private static LinkedList compile0(String programText) 
	throws CompilationException {
		
		Pattern p = Pattern.compile("\"[^\"]+\"|\\([^\\)]+\\)|[^ \r\n]+");
		Matcher m = p.matcher(programText);
		
		ArrayList<String> tokensList = new ArrayList<String>();
		while (m.find()) {
			tokensList.add(m.group(0).trim());
		}
		
		Queue<String> tokensQueue = new LinkedList<String>();
		for (String token : tokensList) {
			if (token.trim().length() > 0) {
				tokensQueue.add(token);
			}
		}
		
		LinkedList rootScope = new LinkedList(); // root scope
		addScope(tokensQueue, rootScope);
		
		return rootScope;
	}
	
	/**
	 * 
	 * @param tokens
	 * @param parentScope
	 */
	private static void addScope(Queue<String> tokens, Queue parentScope) 
	throws CompilationException {
		
		Queue queue = new LinkedList();
		
		while (!tokens.isEmpty()) {
			
			Object token = tokens.poll();
			
			if ("end".equals(token)) {
				
				parentScope.add(token);
				break;
				
			} else if ("else".equals(token)) {

				parentScope.add(token);
				break;
				
			} else if ("elseif".equals(token)) {
				
				parentScope.add(token);
				break;
				
			} else if ("if".equals(token)) {
				
				String expression = tokens.poll();
				
				If_Verb ifVerb = new If_Verb();
				ifVerb.setExpression(expression);
				
				parentScope.add(ifVerb); 
				addScope(tokens, ifVerb.createScope());
				
				// check if elseif or else is defined
				LinkedList ifScope = (LinkedList) ifVerb.getScope();
				Object elseToken = ifScope.peekLast();
				
				if (elseToken instanceof String && 
				(elseToken.equals("elseif") || elseToken.equals("else"))) {
					
					ifScope.pollLast(); // remove elseif or else from if scope
					
					while (elseToken instanceof String && 
					(elseToken.equals("elseif") || elseToken.equals("else"))) {
						
						if (elseToken instanceof String) {
							
							String elseStr = (String) elseToken;
							if ("elseif".equals(elseStr)) {
								
								String exp = tokens.poll();
								ElseIf_Verb elseIfVerb = new ElseIf_Verb();
								elseIfVerb.setExpression(exp);
								ifVerb.addElseIf(elseIfVerb);
								
								addScope(tokens, elseIfVerb.createScope());
								elseToken = elseIfVerb.getScope().pollLast();
								
							} else if ("else".equals(elseStr)) {
								
								Else_Verb elseVerb = new Else_Verb();
								ifVerb.setElse(elseVerb);
								
								addScope(tokens, elseVerb.createScope());
								elseToken = elseVerb.getScope().pollLast();
					
							} 
						}
					}
				}
			} else if ("while".equals(token)) {
				
				String evaluation = tokens.poll();
				
				While_Verb whileVerb = new While_Verb();
				whileVerb.setExpression(evaluation);
				
				parentScope.add(whileVerb);
				addScope(tokens, whileVerb);
				
			} else if (LangCore.isVerb(token)){
				
				try {
					parentScope.add(LangCore.createVerbObject((String) token));
				} catch (Exception e) {
					e.printStackTrace();
					throw new CompilationException(e.getLocalizedMessage());
				}
			
			} else { // variable
				
				parentScope.add(token);
			}
		}	
	}
}
