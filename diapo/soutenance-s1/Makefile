# Makefile diapo
SHELL=/usr/bin/bash
TARGET=main

all:
	@echo "Compiling..."
	@pdflatex $(TARGET).tex > /dev/null
	@pdflatex $(TARGET).tex > /dev/null
	@echo "Done"

debug:
	pdflatex $(TARGET).tex
	pdflatex $(TARGET).tex

clean:
	@echo "Cleaning..."; rm -rf $(TARGET).{aux,nav,vrb,log,out,snm,toc} _minted-$(TARGET)
