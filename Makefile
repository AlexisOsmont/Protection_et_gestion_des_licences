SHELL=/bin/bash
MAKEFLAGS += --no-print-directory

objects = stb fiches dat adr pdd

all: $(objects)
	@echo "Done"

clean: 
	@echo "Cleaning ..."
	@cd STB/; $(MAKE) clean
	@cd STB/fiches-techniques/; $(MAKE) clean
	@cd DAT/main/; $(MAKE) clean
	@cd ADR/; $(MAKE) clean
	@cd PDD/; $(MAKE) clean
	@echo "Done"

stb:
	@echo "Entering into STB/" 	
	@cd STB/; $(MAKE)

pdd:
	@echo "Entering into PDD/" 	
	@cd PDD/; $(MAKE)

fiches:
	@echo "Entering into STB/fiches-techniques" 	
	@cd STB/fiches-techniques/; $(MAKE)
	 
dat:
	@echo "Entering into DAT/" 	
	@cd DAT/main/; $(MAKE)

adr:
	@echo "Entering into ADR/" 	
	@cd ADR/; $(MAKE)

