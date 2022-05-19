# Makefile diapo
SHELL=/usr/bin/bash
TARGET=main

all:
	@echo "Compiling..."
	@pdflatex --shell-escape $(TARGET).tex > /dev/null
	@pdflatex --shell-escape $(TARGET).tex > /dev/null
	@echo "Done"

debug:
	pdflatex --shell-escape $(TARGET).tex
	pdflatex --shell-escape $(TARGET).tex

clean:
	@echo "Cleaning..."; rm -rf $(TARGET).{aux,nav,vrb,log,out,snm,toc,lof} _minted-$(TARGET)
