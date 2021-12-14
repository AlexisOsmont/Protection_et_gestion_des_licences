SHELL=/bin/bash
TARGET=CDR

all:
	@echo "- Compiling $(notdir $(PWD))/$(TARGET).tex"
	@pdflatex -shell-escape $(TARGET).tex > /dev/null
	@pdflatex -shell-escape $(TARGET).tex > /dev/null

debug:	# don't silent command for debugging
	pdflatex -shell-escape $(TARGET).tex
	pdflatex -shell-escape $(TARGET).tex

clean:
	@rm -rf $(TARGET).{pdf,aux,log,out,toc} _minted-$(TARGET) 
