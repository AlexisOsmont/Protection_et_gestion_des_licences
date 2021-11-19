MAKEFLAGS += --no-print-directory

objects = stb fiches dat

all: $(objects)
	@echo "Done"

clean: 
	@echo "Cleaning ..."
	@cd STB/; $(MAKE) clean
	@cd STB/fiches-techniques/; $(MAKE) clean
	@cd DAT/main/; $(MAKE) clean
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
