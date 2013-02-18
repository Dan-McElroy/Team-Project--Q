main.pdf: main.tex references.bib
	pdflatex main.tex
	bibtex main.aux
	pdflatex main.tex
	pdflatex main.tex

clean:
	rm -rf *.log
	rm -rf *.pdf
	rm -rf *.aux
	rm -rf *.bbl
	rm -rf *.blg
