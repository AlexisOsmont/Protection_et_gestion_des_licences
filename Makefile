MAKEFLAGS += --no-print-directory

objects = stb fiches dat

all: $(objects)
	@echo "Done"

clean: 
	@echo "Cleaning ..."
	@rm -rf {STB,DAT}/{*.aux,*.log,*.out,*.run.xml,*.bbl,*-blx.bib,*.blg,*.pdf,*.toc}	
	@echo "Done"

stb:
	@echo "Entering into STB/" 	
	@cd STB/; $(MAKE)

fiches:
	@echo "Entering into STB/fiches-techniques" 	
	@cd STB/fiches-techniques/; $(MAKE)
	 
dat:
	@echo "Entering into DAT/" 	
	@cd DAT/main/; $(MAKE)
