######################################################################
# mSpray SP Form Generator
######################################################################
This program can be used for downloading all of the mSpray SP Forms
onto one's computer. Currently, the online spreadsheet linked to this
software only holds test data and fake names, but that is for security
reasons as the information is not being officially released yet.
Regardless, the program can be used to pull all of that data down and
put it into SP forms anyway.

The program can be accessed by clicking on the .sh files in this
folder:
    * Running the downloader and generator can be done by clicking on
          the downloadFiles.sh file.
    * Removing all of the auto-generated files from the folder and all
          of the temporary files can be done by clicking on the
          cleanFolder.sh file.

Alternatively, if users prefer to access the files via command line,
they can do the following:
    * Run the downloader and generator by executing the following
          command in this folder:

          make download

    * Remove all of the auto-generated Java class files, temporary
          files, and all of the generated forms by executing the
          following command in this folder:

          make clean


######################################################################
# Acknowledgements
######################################################################
The iText Open Source Java library was extremely helpful in making
this program possible - their JAR files were included in the "lib"
folder of this project, and were instrumental in the auto-completion
of the SP forms' PDF files.

