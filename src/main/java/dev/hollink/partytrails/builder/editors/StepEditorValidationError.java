package dev.hollink.partytrails.builder.editors;

import lombok.Value;

@Value
public class StepEditorValidationError
{
	int stepIndex;
	String field;
	String errorMessage;
}
