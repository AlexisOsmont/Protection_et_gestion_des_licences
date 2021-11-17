# Latex Makefile

all:
	cd Obfuscation; pdflatex obfus.tex && bibtex obfus && pdflatex obfus.tex && pdflatex obfus.tex
	cd Licence; pdflatex -shell-escape licence.tex
	cd Base-de-donnees; pdflatex bdd.tex
	cd code-injection; pdflatex code_injection.tex

clean:
	cd Obfuscation; rm *.aux *.log *.out *.run.xml *.bbl *-blx.bib *.blg *.pdf
	cd Licence; rm *.aux *.log *.pdf; rm -r _minted-licence
	cd Base-de-donnees; rm *.aux *.log *.out *.pdf
	cd code-injection; rm *.aux *.log *.out *.pdf
