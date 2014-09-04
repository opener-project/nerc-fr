# NERC-fr

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

* Maven 3.0 or newer

## Installation

Installation is not really necessary to start using the OpenNLP command line tool.
Simply execute the *opennlp/bin/openlp* script.

## Usage

You can use the extended OpenNLP version as common version as described at the
<a href="https://opennlp.apache.org/documentation/1.5.3/manual/opennlp.html">Apache OpenNLP Developer Documentation</a>.

If you want to train a new NERC model with PoS feature extraction, you have to
provide a custom feature generator. The trained NERC model will store the PoS model
inside, so the PoS model is no longer needed. You can define a custom feature generator
either via API or via an xml descriptor file.

#### Feature Generation defined by API

The custom generator must be used for training and for detecting the names.
If the feature generation during training time and detection time is different
the name finder might not be able to detect names. The following lines show
how to construct a custom feature generator:

```java
AdaptiveFeatureGenerator featureGenerator = new CachedFeatureGenerator(
         new AdaptiveFeatureGenerator[]{
           new WindowFeatureGenerator(new TokenFeatureGenerator(), 2, 2),
           new WindowFeatureGenerator(new TokenClassFeatureGenerator(true), 2, 2),
           new WindowFeatureGenerator(new PoSFeatureGenerator("/pos-model-path.bin", TagSet.NONE), 2, 2),
           new OutcomePriorFeatureGenerator(),
           new PreviousMapFeatureGenerator(),
           new BigramNameFeatureGenerator(),
           new SentenceFeatureGenerator(true, false)
           });
```

The javadoc of the feature generator classes explain what the individual
feature generators do. To write a custom feature generator please implement
the AdaptiveFeatureGenerator interface or if it must not be adaptive extend
the FeatureGeneratorAdapter. The train method which should be used is defined as

```java
public static TokenNameFinderModel train(String languageCode, String type, ObjectStream<NameSample> samples, 
       TrainingParameters trainParams, AdaptiveFeatureGenerator generator, final Map<String, Object> resources) throws IOException
```

and can take feature generator as an argument. To detect names the model which
was returned from the train method and the feature generator must be passed to
the NameFinderME constructor.

```java
new NameFinderME(model, featureGenerator, NameFinderME.DEFAULT_BEAM_SIZE);
```

#### Feature Generation defined by XML Descriptor

OpenNLP can also use a xml descriptor file to configure the feature generation.
The descriptor file is stored inside the model after training and the feature
generators are configured correctly when the name finder is instantiated. The
following sample shows a xml descriptor:

```xml
<generators>
  <cache> 
    <generators>
      <window prevLength = "2" nextLength = "2">          
        <tokenclass/>
      </window>
      <window prevLength = "2" nextLength = "2">                
        <token/>
      </window>
      <window prevLength = "2" nextLength = "2">                
        <pos model="/pos-model-path.bin"/>
      </window>
      <definition/>
      <prevmap/>
      <bigram/>
      <sentence begin="true" end="false"/>
    </generators>
  </cache> 
</generators>
```

The root element must be generators, each sub-element adds a feature generator
to the configuration. The sample xml is equivalent to the generators defined by
the API above.

The following table shows the supported elements:


| Element      | Aggregated | Attributes                                                                                    |
| ------------ | ---------- | --------------------------------------------------------------------------------------------- |
| generators   | yes        | none                                                                                          |
| cache        | yes        | none                                                                                          |
| pos          | no         | *model* specify the PoS model path                                                            |
| charngram    | no         | *min* and *max* specify the length of the generated character ngrams                          |
| definition   | no         | none                                                                                          |
| dictionary   | no         | *dict* is the key of the dictionary resource to use, and *prefix* is a feature prefix string  |
| prevmap      | no         | none                                                                                          |
| sentence     | no         | *begin* and *end* to generate begin or end features, both are optional and are boolean values |
| tokenclass   | no         | none                                                                                          |
| token        | no         | none                                                                                          |
| bigram       | no         | none                                                                                          |
| tokenpattern | no         | none                                                                                          |
| window       | yes        | *prevLength* and *nextLength* must be integers ans specify the window size                    |
| custom       | no         | *class* is the name of the feature generator class which will be loaded                       |

Aggregated feature generators can contain other generators, like the cache or the window feature generator in the sample.

## Compile the source code

The source code is a Maven project, go to the *core/* folder and execute this command:

```
$ mvn package
```

## Add the extended library to your code

Firt, you need to install the source code into your local Maven repository. Go to the
*core/* folder and execute this command:

```
$ mvn install
```

Now you can add the extended library as Maven dependency:

```xml
<dependency>
  <groupId>org.vicomtech</groupId>
  <artifactId>opennlp-tools</artifactId>
  <version>custom-0.0.1</version>
</dependency>
```

## Contributing

1. Pull it
2. Create your feature branch (`git checkout -b features/my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin features/my-new-feature`)
5. If you're confident, merge your changes into master.
