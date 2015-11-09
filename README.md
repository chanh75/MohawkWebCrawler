# MohawkWebCrawler
Simple scripting language to control web crawling and webpage data scraping.

Here's an example of scraping Google stock data off of Yahoo Finance:

	var price
	var prevclose

	var hdate
	var hopen
	var hhigh
	var hlow
	var hclose
	
	geturl "http://finance.yahoo.com/q?s={SYMBOL}&ql=1"
	
	gotext "({SYMBOL})"
	
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
	
	while (nextrow)
		nextcol
		gettext hdate
		
		nextcol
		gettext hopen
		
		nextcol
		gettext hhigh
		
		nextcol
		gettext hlow
		
		nextcol
		gettext hclose
		
		print "line>> date={hdate}, open={hopen}, high={hhigh}, low={hlow}"
		commitrow5 "hrow" hdate hopen hhigh hlow hclose
	end
