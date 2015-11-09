# MohawkWebCrawler
Mohawk WebCrawler is a imple scripting language to control web page and PDF data scraping.  PDF data scraping is down by transforming the PDF to a HTML code.

Here's an example of scraping Google stock data off of Yahoo Finance:
	
	/* declare variables */
	var price
	var prevclose
	var hdate
	var hopen
	
	geturl "http://finance.yahoo.com/q?s={SYMBOL}&ql=1"  // go to web page
	
	gotext "({SYMBOL})" // find symbol
	
	nextdiv
	gettext price
	print "price>> {price}"
	commit "price" price
	
	gotext "Prev Close:"
	nextcol
	gettext prevclose
	print "previous close>> {prevclose}"
	commit "prevclose" prevclose
	
	// Historical Prices page 
	geturl "http://finance.yahoo.com/q/hp?s={SYMBOL}+Historical+Prices"
	gotext "Historical Prices"
	nexttext "Last"
	
	nexttable
	nexttable
	nexttable //print "{#CURSOR}"
	nextrow //print "{#CURSOR}"
	
	while (nextrow) // while table has rows
	
		nextcol
		gettext hdate
		
		if (hdate ~has "Close price adjusted for dividends and splits.")
			break
		else
			nextcol
			gettext hopen
		
			print "line>> date={hdate}, open={hopen}"
			commitrow2 "hrow" hdate hopen
		end
	end
