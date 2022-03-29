SHELL=/bin/bash
TARGET=Manuel

all:
	@echo "- Compiling $(notdir $(PWD))/$(TARGET).tex"
	@pdflatex $(TARGET).tex > /dev/null
	@pdflatex $(TARGET).tex > /dev/null

debug:	# don't silent command for debugging
	pdflatex $(TARGET).tex
	pdflatex $(TARGET).tex

clean:
	@rm -rf $(TARGET).{pdf,aux,log,out,toc} 
