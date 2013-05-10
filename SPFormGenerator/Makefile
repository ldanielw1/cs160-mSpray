compile:
	@-cp res/* .
	@-cp src/* .
	@-javac -cp ".:lib/*" *.java 2> /dev/null > /dev/null

download:
	@-cp res/* .
	@-cp src/* .
	@-javac -cp ".:lib/*" *.java 2> /dev/null > /dev/null
	@-java -cp ".:lib/*" DownloadMSprayFiles

clean:
	@-rm -rf sp1 2> /dev/null > /dev/null
	@-rm -rf sp2 2> /dev/null > /dev/null
	@-rm -rf sp3 2> /dev/null > /dev/null
	@-rm *.pdf 2> /dev/null > /dev/null
	@-rm *.java 2> /dev/null > /dev/null
	@-rm *.class 2> /dev/null > /dev/null

really-clean: clean
