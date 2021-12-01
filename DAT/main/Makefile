# Makefile création DAT

TARGET=DAT

all:
	@echo "- Compiling $(notdir $(PWD))/$(TARGET).tex"
	@pdflatex $(TARGET).tex > /dev/null
	@bibtex $(TARGET) > /dev/null
	@pdflatex $(TARGET).tex > /dev/null
	@pdflatex $(TARGET).tex > /dev/null

debug:
	pdflatex $(TARGET).tex 
	bibtex $(TARGET) 
	pdflatex $(TARGET).tex
	pdflatex $(TARGET).tex

clean:
	@rm -rf $(TARGET)-blx.bib $(TARGET).{aux,out,log,toc,pdf,bbl,blg,run.xml}
