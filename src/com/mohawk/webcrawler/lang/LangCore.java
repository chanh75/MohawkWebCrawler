package com.mohawk.webcrawler.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import com.mohawk.webcrawler.Tokenizer;
import com.mohawk.webcrawler.lang.BaseVerb.ReturnType;

public class LangCore {

	private static final String VERB_PACKAGE = "com.mohawk.webcrawler.lang.verb.";
	private static final String OPERATOR_PACKAGE = "com.mohawk.webcrawler.lang.operator.";
	
	private static final HashMap<String, String> VERB_CLASS_MAP = new HashMap<String, String>();
	private static final HashMap<String, String> OPERATOR_CLASS_MAP = new HashMap<String, String>();
	
	static {
		VERB_CLASS_MAP.put("var", VERB_PACKAGE + "Var_Verb");
		VERB_CLASS_MAP.put("vardef", VERB_PACKAGE + "VarDef_Verb");
		
		VERB_CLASS_MAP.put("geturl", VERB_PACKAGE + "GetUrl_Verb");
		VERB_CLASS_MAP.put("getpdf", VERB_PACKAGE + "GetPdf_Verb");
		VERB_CLASS_MAP.put("gettag", VERB_PACKAGE + "GetTag_Verb");
		VERB_CLASS_MAP.put("gotext", VERB_PACKAGE + "GoText_Verb");
		VERB_CLASS_MAP.put("gettext", VERB_PACKAGE + "GetText_Verb");
		
		VERB_CLASS_MAP.put("nextdiv", VERB_PACKAGE + "NextDiv_Verb");
		VERB_CLASS_MAP.put("nexth2", VERB_PACKAGE + "NextH2_Verb");
		VERB_CLASS_MAP.put("nexth3", VERB_PACKAGE + "NextH3_Verb");
		VERB_CLASS_MAP.put("nexttext", VERB_PACKAGE + "NextText_Verb");
		VERB_CLASS_MAP.put("nexttable", VERB_PACKAGE + "NextTable_Verb");
		VERB_CLASS_MAP.put("nexttablebody", VERB_PACKAGE + "NextTableBody_Verb");
		VERB_CLASS_MAP.put("nextrow", VERB_PACKAGE + "NextTableRow_Verb");
		VERB_CLASS_MAP.put("nextcol", VERB_PACKAGE + "NextTableCol_Verb");
		
		VERB_CLASS_MAP.put("sethead1", VERB_PACKAGE + "SetHead1_Verb");
		VERB_CLASS_MAP.put("sethead2", VERB_PACKAGE + "SetHead2_Verb");
		VERB_CLASS_MAP.put("setdesc", VERB_PACKAGE + "SetDesc_Verb");
		VERB_CLASS_MAP.put("setprice", VERB_PACKAGE + "SetPrice_Verb");
		VERB_CLASS_MAP.put("resethead1", VERB_PACKAGE + "ResetHead1_Verb");
		VERB_CLASS_MAP.put("resethead2", VERB_PACKAGE + "ResetHead2_Verb");
		
		VERB_CLASS_MAP.put("commit", VERB_PACKAGE + "Commit_Verb");
		VERB_CLASS_MAP.put("commitrow5", VERB_PACKAGE + "CommitRow5_Verb");
		VERB_CLASS_MAP.put("commitservice", VERB_PACKAGE + "CommitService_Verb");
		VERB_CLASS_MAP.put("break", VERB_PACKAGE + "Break_Verb");
		VERB_CLASS_MAP.put("exit", VERB_PACKAGE + "Exit_Verb");
		VERB_CLASS_MAP.put("print", VERB_PACKAGE + "Print_Verb");
		
		VERB_CLASS_MAP.put("nextsvgf", VERB_PACKAGE + "NextSvgWithFilter_Verb");
		VERB_CLASS_MAP.put("nextsvgg", VERB_PACKAGE + "NextSvgG_Verb");
		VERB_CLASS_MAP.put("nextsvggf", VERB_PACKAGE + "NextSvgGWithFilter_Verb");
		VERB_CLASS_MAP.put("nextsvgtext", VERB_PACKAGE + "NextSvgText_Verb");
		VERB_CLASS_MAP.put("svgxmax", VERB_PACKAGE + "SvgXMax_Verb");
		VERB_CLASS_MAP.put("length", VERB_PACKAGE + "Length_Verb");
		VERB_CLASS_MAP.put("wordcount", VERB_PACKAGE + "WordCount_Verb");
		
		OPERATOR_CLASS_MAP.put("=", OPERATOR_PACKAGE + "Assign_Operator");
		OPERATOR_CLASS_MAP.put("==", OPERATOR_PACKAGE + "Equals_Operator");
		OPERATOR_CLASS_MAP.put("!=", OPERATOR_PACKAGE + "NotEquals_Operator");
		OPERATOR_CLASS_MAP.put("<", OPERATOR_PACKAGE + "LessThan_Operator");
		OPERATOR_CLASS_MAP.put(">", OPERATOR_PACKAGE + "GreaterThan_Operator");
		OPERATOR_CLASS_MAP.put("+=", OPERATOR_PACKAGE + "AddThenAssign_Operator");
		OPERATOR_CLASS_MAP.put("!", OPERATOR_PACKAGE + "InvertBoolean_Operator");
		OPERATOR_CLASS_MAP.put("++", OPERATOR_PACKAGE + "PlusPlusBefore_Operator");
		OPERATOR_CLASS_MAP.put("~has", OPERATOR_PACKAGE + "HasInSet_Operator");
		OPERATOR_CLASS_MAP.put("~between", OPERATOR_PACKAGE + "BetweenRange_Operator");
		OPERATOR_CLASS_MAP.put("&&", OPERATOR_PACKAGE + "LogicalAnd_Operator");
		OPERATOR_CLASS_MAP.put("||", OPERATOR_PACKAGE + "LogicalOr_Operator");
	}
	
	
	/**
	 * 
	 * @param scriptContext
	 * @param exp
	 * @return
	 * @throws LanguageException
	 */
	public static boolean evaluateExpression(ScriptContext scriptContext, String exp) throws LanguageException {
		
		if (!(exp.startsWith("(") && exp.endsWith(")"))) {
			throw new LanguageException("Is not expression>> " + exp);
		}
		
		// remove ()
		String exp0 = exp.substring(1, exp.length() - 1);
		
		LinkedList tokens = (new Tokenizer()).tokenize(exp0);
		//System.out.println("tokens>> " + tokens);
		
		int index = 0;
		
		do {
			Object tokenObj = tokens.get(index);
			//System.out.println("tokens>> [" + tokens + "]");
			//System.out.println("token>> [" + tokenObj + "]");
			
			if (tokenObj instanceof Boolean) {
				index++;
				continue;
			}
			
			String token = (String) tokenObj;
			
			if (hasUnaryOperator(token)) { // operators ++ or --
			
				String[] operation = parseUnaryOperator(token);
				BaseOperator operatorObj = createOperatorObject(operation[0]);
				try {
					operatorObj.run(scriptContext, operation[1]);
					
					tokens.remove(index);
					tokens.add(index, operation[1]);
					index++;
				} catch (Exception e) {
					e.printStackTrace();
					throw new LanguageException(e.getLocalizedMessage());
				}
				
			} else if (isVerb(token)) { // invoke verb
				
				tokens.remove(index);
				BaseVerb verbObject = createVerbObject(token);
				
				if (verbObject.returnType() == ReturnType.VOID) {
					throw new LanguageException("Verb with void return cannot be in expression.");
				}
				
				int paramsRequired = verbObject.numOfParams();
				if (paramsRequired == 0) {
					tokens.add(invokeVerb(verbObject, scriptContext));
				} else if (paramsRequired > 0) {
					String[] params = new String[paramsRequired];
					for (int j = 0; j < paramsRequired; j++) {
						params[j] = (String) tokens.remove(index + j);
					}
					tokens.add(index, invokeVerb(verbObject, params, scriptContext));
				}
				index++;
				
			} else if (isConditionalOperator(token)) {
				
				Boolean p1 = (Boolean) tokens.get(index - 1);
				Object p2 = tokens.get(index + 1);
				
				if (!(p2 instanceof Boolean)) {
					index++;
				} else {
					try {
						BaseOperator opObj = createOperatorObject(token);
						Boolean result = (Boolean) opObj.run(scriptContext, p1, p2);
						if (!result) {
							return false;
						} else {
							tokens.remove(index - 1);
							tokens.remove(index - 1);
							tokens.remove(index - 1);
							tokens.add(index - 1, result);
						}
						index++;
					} catch (Exception e) {
						e.printStackTrace();
						throw new LanguageException(e.getLocalizedMessage());
					}
				}
			
			} else if (isOperator(token)) {
				
				BaseOperator opObj = createOperatorObject(token);
				Object[] params = new Object[2];
				params[0] = tokens.remove(index - 1);
				tokens.remove(index - 1); // remove operator
				params[1] = tokens.remove(index - 1);
				try {
					Boolean result = (Boolean) opObj.run(scriptContext, params);
					tokens.add(index - 1, result);
					index = 0; // reset
				} catch (Exception e) {
					e.printStackTrace();
					throw new LanguageException(e.getLocalizedMessage());
				}
				
			} else if (isLiteral(token)) {
				
				index++;
				
			} else if (scriptContext.hasLocalVariable(token)) {
				
				index++;
			
			} else {
				throw new LanguageException("Unable to resolve token '" + token + "' in expression>> " + exp);
			}
			
		} while (tokens.size() > 1);
		
		return (Boolean) tokens.remove();
	}
	
	/**
	 * 
	 * @param scriptContext
	 * @param param
	 * @return
	 * @throws LanguageException
	 */
	public static Object resolveParameter(ScriptContext scriptContext, Object param) 
	throws LanguageException {
		
		// if string literal, do variable injection
		if (param instanceof String) {
			String sparam = (String) param;
			
			if (LangCore.isStringLiteral(sparam)) {
				
				return LangCore.createStringLiteral(LangCore.injectVars(scriptContext, sparam));
			
			} /*else if (LangCore.isConstant(sparam)) {
				
				return LangCore.createConstant(pageContext, sparam);
				
			*/else if (LangCore.isLiteral(sparam)) {
				
				return LangCore.createLiteral(sparam);
			
			} else if (scriptContext.hasLocalVariable(sparam)) {
				
				return scriptContext.getLocalVariable(sparam);
			}
		} else if (param instanceof Boolean) {
			return param;
		} else if (param instanceof Integer) {
			return param;
		} else if (param instanceof Double) {
			return param;
		} else if (param instanceof Variable) {
			return param;
		}
		
		throw new LanguageException("Unable to resolve parameter>> " + param);
	}
	
	public static boolean isOperator(String op) {
		return OPERATOR_CLASS_MAP.containsKey(op);
	}
	
	public static boolean isConditionalOperator(String op) {
		return OPERATOR_CLASS_MAP.containsKey(op) && ("&&".equals(op) || "||".equals(op));
	}
	
	private static boolean hasUnaryOperator(String exp) {
		return ((exp.startsWith("!") && !exp.startsWith("!=")) || exp.startsWith("++") || exp.endsWith("++") 
				|| exp.startsWith("--") || exp.endsWith("--"));
	}
	
	/**
	 * 
	 * @param exp
	 * @return
	 * @throws LanguageException
	 */
	private static String[] parseUnaryOperator(String exp) throws LanguageException {
		if (exp.startsWith("!")) {
			return new String[] { exp.substring(0, 1), exp.substring(1) };
		} else if (exp.startsWith("++")) {
			return new String[] { exp.substring(0, 2), exp.substring(2) };
		}
		
		throw new LanguageException("Unable to parse unary in expression>> " + exp);
	}
	
	/**
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isLiteral(Object object) {
		
		if (object instanceof String) {
			String token = ((String) object).trim();
			if (token.startsWith("\"") && token.endsWith("\"")) {
				return true;
			} else if ("true".equals(token) || "false".equals(token)) {
				return true;
			} else if (token.startsWith("[") && token.endsWith("]")) {
				return true;
			} else {
				return isNumberLiteral((String) token);
			}
		} else if (object instanceof Integer) {
			return true;
		} else if (object instanceof Boolean) {
			return true;
		} else if (object instanceof Double) {
			return true;
		//} else if (object instanceof Object[]) {
		//	return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param token
	 * @return
	 * @throws LanguageException
	 */
	public static Object createLiteral(Object token) throws LanguageException {
		
		if (token instanceof String) {
			
			String s = ((String) token).replace("\r\n","").replace("\t", "").trim();
			
			if (s.startsWith("\"") && s.endsWith("\"")) { // string
			
				return s.substring(1, s.length() - 1);
			
			} else if ("true".equals(s) || "false".equals(s)) { // boolean
				
				return new Boolean(s);
			
			} else if (s.startsWith("[") && s.endsWith("]")) { // set
				
				Object[] tokens = s.substring(1, s.length() - 1).split(",");
				int len = tokens.length;
				Object[] ret = new Object[len];
				for(int i = 0; i < len; i++) {
					ret[i] = LangCore.createLiteral(tokens[i]);
				}
				return ret;
			
			} else {
				for (int i = 0; i < 2; i++) {
					try {
						if (i == 0) {
							return new Integer((String) token);
						} else if (i == 1) {
							return new Double((String) token);
						}
					} catch (Exception e) {
					}
				}
			}
		} else if (token instanceof Number) {
			return token;
		}
		
		throw new LanguageException("Unable to convert to literal value>> " + token);
	}
	
	
	
	private static boolean isStringLiteral(Object s) {
		if (s instanceof String) {
			String l = (String) s;
			return l.startsWith("\"") && l.endsWith("\"");
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	private static boolean isNumberLiteral(String s) {
		
		for (int i = 0; i < 2; i++) {
			try {
				if (i == 0) {
					Integer.parseInt(s);
				} else if (i == 1) {
					Double.parseDouble(s);
				}
				return true;
			} catch (Exception e) {
			}
		}
		return false;
	} 
	
	private static String createStringLiteral(Object param) {
		String p = param.toString();
		
		int s = p.indexOf("\"");
		int e = p.lastIndexOf("\"");
		return p.substring(s + 1, e);
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 * @throws LanguageException
	 *
	private static Number createNumberLiteral(Object s) throws LanguageException {
		if (s instanceof Integer) {
			return (Integer) s;
		} else if (s instanceof Double) {
			return (Double) s;
		} else if (s instanceof String) {
			try {
				String sval = (String) s;
				int i = Integer.parseInt(sval);
				if (String.valueOf(i).equals(sval)) {
					return i;
				}
				double d = Double.parseDouble(sval); 
				if (String.valueOf(d).equals(sval)) {
					return d;
				}
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
		throw new LanguageException("Unable to make number literal from>> " + s);
	}
	*/
	/*
	private static String createConstant(ScriptContext pageContext, String param) 
	throws LanguageException {
		return (String) pageContext.getConfig().getConstant(LangCore.parseConstant(param));
	}
	*/
	private static String injectVars(ScriptContext c, String s) throws LanguageException {
		
		if (!LangCore.isStringLiteral(s)) {
			throw new LanguageException("Variable injection can only happen on string literals.");
		}
			
		StringBuffer sb = new StringBuffer(s);
		
		int s1 = -1;
		do {
			s1 = sb.indexOf("{");
		
			if (s1 != -1) {
				int s2 = sb.indexOf("}", s1 + 1);
				if (s2 == -1) {
					throw new LanguageException("Variable injection not closed with '}'");
				}
				
				String var = sb.substring(s1 + 1, s2);
				Object value = null;
				if ("#CURSOR".equals(var)) {
					int pos = c.getCursorPosition();
					value = c.getDocumentHtml().substring(pos, pos + 200);
				} else {
					Variable varObj = c.getLocalVariable(var);
					if (varObj != null) {
						value = varObj.getValue();
					}
				}
				
				if (value == null) {
					sb.replace(s1, s2 + 1, "null");
				} else if (value instanceof Object[]) {
					sb.replace(s1, s2 + 1, Arrays.toString((Object[]) value));
				} else {
					sb.replace(s1, s2 + 1, String.valueOf(value));
				}
				
			}
		} while (s1 != -1);
		
		return sb.toString();
	}
	
	public static boolean isVerb(Object s) {
		if (s instanceof String) {
			return VERB_CLASS_MAP.containsKey((String) s);
		} else {
			return false;
		}
	}
	
	private static String resolveVerbSimpleClassName(String simpleName) {
		return VERB_CLASS_MAP.get(simpleName);
	}
	
	private static String resolveOperatorSimpleClassName(String simpleName) {
		return OPERATOR_CLASS_MAP.get(simpleName);
	}
	
	protected static Object invokeVerb(String verbName, ScriptContext context) 
	throws LanguageException {
		try {
			BaseVerb verbObj = createVerbObject(verbName);
			return verbObj.run(context, null);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LanguageException(e.getLocalizedMessage());
		}
	}
	
	protected static Object invokeVerb(BaseVerb verbObj, ScriptContext context) 
	throws LanguageException {
		try {
			return verbObj.run(context, null);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LanguageException(e.getLocalizedMessage());
		}
	}
	
	protected static Object invokeVerb(BaseVerb verbObj, Object[] params, ScriptContext context) 
	throws LanguageException {
		try {
			return verbObj.run(context, params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LanguageException(e.getLocalizedMessage());
		}
	}
	
	public static BaseVerb createVerbObject(String verbName) throws LanguageException {
		try {
			String fullClassName = resolveVerbSimpleClassName(verbName);
			if (fullClassName == null) {
				throw new LanguageException("Verb not found>> " + verbName);
			}
			return (BaseVerb) Class.forName(fullClassName).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new LanguageException(e.getLocalizedMessage());
		}
	}
	
	public static BaseOperator createOperatorObject(String operator) throws LanguageException {
		try {
			String fullClassName = resolveOperatorSimpleClassName(operator);
			if (fullClassName == null) {
				throw new LanguageException("Operator not found>> " + operator);
			}
			return (BaseOperator) Class.forName(fullClassName).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new LanguageException(e.getLocalizedMessage());
		}
	}
	
	/**
	 * 
	 * @param tokens
	 * @return
	 */
	private LinkedList orderExpression(ArrayList<String> tokens) {
		
		LinkedList linkedList = new LinkedList();
		ArrayList<String> subExpTokens = new ArrayList<String>();
		
		int len = tokens.size();
		for (int i = 0; i < len; i++) {
			String token = tokens.get(i);
			
			if ("&&".equals(token) || "||".equals(token)) {
				linkedList.add(subExpTokens);
				linkedList.add(token);
				subExpTokens = new ArrayList<String>();
			} else {
				subExpTokens.add(token);
			}
		}
		
		if (subExpTokens.size() > 0) {
			linkedList.add(subExpTokens);
		}
		return linkedList;
	}
}
