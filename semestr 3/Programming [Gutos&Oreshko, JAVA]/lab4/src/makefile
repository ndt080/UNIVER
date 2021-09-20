# makefile lab4
.SUFFIXES: .class .java

PACK=lab4
TARG=Main
JC=javac -g
JM=java -ea -jar
JR=jar -cfe

OBJ=$(PACK)\Main.class \
	$(PACK)\Group.class \
    $(PACK)\Student.class

.java.class:
	$(JC) $<

run: build
	$(JM) $(TARG).jar

build: 	$(OBJ)
	$(JR) $(TARG).jar $(PACK).Main $(OBJ)

clean:
	for %%f in ($(OBJ)) do del %%f
	del $(TARG).jar

$(PACK)\Main.class: $(PACK)\Main.java makefile
$(PACK)\Group.class: $(PACK)\Group.java makefile
$(PACK)\Student.class: $(PACK)\Student.java makefile

# eof makefile lab4
