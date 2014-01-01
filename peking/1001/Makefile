#
# Makefile
# Xiaowei Song, 2013-12-30 22:17
#

all: 
	@echo "Makefile needs your attention"
	rm test
	lcpp src.cpp -o test
	@for t in test.in test.in2 test.in3; do \
		echo $$t ; \
		./test < $$t > $${t}.out ; \
		./genTrue.sh $$t > $${t}.true ; \
		./cmp.sh $${t}.out $${t}.true ; \
		echo '---------------' ; \
	done
	##diff -y test.out test.true
	

# vim:ft=make
#

