SHELL=/bin/bash

all:
	@echo "- Compiling $(notdir $(PWD))/STB.tex"
	@pdflatex STB.tex > /dev/null
	@pdflatex STB.tex > /dev/null

debug:	# don't silent command for debugging
	pdflatex STB.tex
	pdflatex STB.tex

clean:
	@rm -f STB.pdf STB.aux STB.log STB.out STB.toc 
