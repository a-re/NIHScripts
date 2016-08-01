# NIH Scripts
During my summer internship at NIH, I needed to write scripts to extract data from some of the processes we did. These are all coded in Java 1.7 using Eclipse Neon.

### FSC to CSV
This script extracts FSC data from a coordinates.xml file outputted by RELION during the postprocessing step to a human-readable .csv file to be processed in Excel or some other program. We needed this script to make FSC plots of our runs to show our actual resolution, instead of just telling the resolution as a number. 

### CTF to CSV
This script extracts CTF information from a micrographs.star file outputted by RELION's CTF estimation step. In our project, we compared CTFFIND4 and GCTF and measured the difference of the programs in the final reconstructions. To make graphs comparing the two algorithms, I wrote a script to read the micrographs.csv file and write out a .csv file containing the DefocusU and DefocusV parameters so we could compare how each CTF program generates these parameters.
***
If you would like to view our final poster, You can download it [here](https://cloud.restifo.co/index.php/s/PK8GQOW4M3ej4YT "Poster download")

If you have any questions, contact calix1999@gmail.com