# NER Base

This repository contains a supervised model NERC model for French trained
with an extended version of Apache OpenNLP to support PoS features extraction.

This repository folder structure is organized as follows:

* models:

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;NERC and PoS model for French. The NERC model has the PoS model inside, so the PoS model is not really necessary.

* core:

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;the source code of the extended Apache OpenNLP library.

* opennlp:

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;the compiled Apache OpenNLP library with command line utility.

* TSD2014preprint619.pdf:

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;the paper where the research work is described.

## Requirements

* Java 1.7 or newer

Development requirements:

* Maven

## Installation

Installation is not really necessary to start using the command line tool.