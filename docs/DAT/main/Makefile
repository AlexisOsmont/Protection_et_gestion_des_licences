# Makefile création DAT
SHELL=/bin/bash
TARGET=DAT

all:
	@echo "- Compiling $(notdir $(PWD))/$(TARGET).tex"
	@pdflatex -shell-escape $(TARGET).tex > /dev/null
	@bibtex $(TARGET) > /dev/null
	@pdflatex -shell-escape $(TARGET).tex > /dev/null
	@pdflatex -shell-escape $(TARGET).tex > /dev/null

debug:
	pdflatex -shell-escape $(TARGET).tex 
	bibtex $(TARGET) 
	pdflatex -shell-escape $(TARGET).tex 
	pdflatex -shell-escape $(TARGET).tex 

clean:
	@rm -rf _minted-$(TARGET) $(TARGET)-blx.bib $(TARGET).{aux,out,log,toc,pdf,bbl,blg,run.xml,listing}
