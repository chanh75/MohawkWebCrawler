REM
REM This example runs data scraping for Google stock.
REM
REM Requires Maven
REM

setlocal

mvn exec:java -Dexec.mainClass="com.mohawk.webcrawler.Main" -Dexec.args="-d -s .\yahoo_finance_stocks.txt -v {SYMBOL:\"GOOG\"}"

endlocal