package org.vicomtech.cmdline.namefind;

import opennlp.tools.cmdline.ArgumentParser.OptionalParameter;
import opennlp.tools.cmdline.ArgumentParser.ParameterDescription;
import opennlp.tools.cmdline.params.TrainingToolParams;

public interface TrainerToolParams extends TrainingParams, TrainingToolParams {
    @OptionalParameter
    @ParameterDescription(valueName = "types", description = "name types to use for training")
    String getNameTypes();
}
