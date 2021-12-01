SHELL=/bin/bash

all:
	@cd Obfuscation; pdflatex obfus.tex > /dev/null && bibtex obfus > /dev/null && pdflatex obfus.tex > /dev/null && pdflatex obfus.tex > /dev/null
	@echo "- Compiling $(notdir $(PWD))/obfus.tex"
	@cd Licence; pdflatex -shell-escape licence.tex > /dev/null
	@echo "- Compiling $(notdir $(PWD))/licence.tex"
	@cd Base-de-donnees; pdflatex bdd.tex > /dev/null
	@echo "- Compiling $(notdir $(PWD))/bdd.tex"
	@cd code-injection; pdflatex code_injection.tex > /dev/null
	@echo "- Compiling $(notdir $(PWD))/code_injection.tex"
	@cd generation-de-certificats; pdflatex -shell-escape generation-de-certificats.tex > /dev/null
	@echo "- Compiling $(notdir $(PWD))/generation-de-certificats.tex"

clean:
	@cd Obfuscation; rm -f *.aux *.log *.out *.run.xml *.bbl *-blx.bib *.blg *.pdf
	@cd Licence; rm -f *.aux *.log *.pdf; rm -rf _minted-licence 
	@cd Base-de-donnees; rm -f *.aux *.log *.out *.pdf
	@cd code-injection; rm -f *.aux *.log *.out *.pdf 
	@cd generation-de-certificats; rm -f *.aux *.log *.out *.pdf *.pyg; rm -rf _minted-generation-de-certificats
