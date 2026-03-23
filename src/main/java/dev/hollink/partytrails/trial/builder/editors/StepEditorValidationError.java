package dev.hollink.partytrails.trial.builder.editors;

import lombok.Value;

@Value
public class StepEditorValidationError
{
	int stepIndex;
	String field;
	String errorMessage;
}
