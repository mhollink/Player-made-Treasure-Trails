package dev.hollink.partytrails.builder;

import dev.hollink.partytrails.builder.editors.StepEditor;
import dev.hollink.partytrails.builder.editors.StepEditorFactory;
import dev.hollink.partytrails.builder.editors.StepEditorValidationError;
import dev.hollink.partytrails.data.StepType;
import dev.hollink.partytrails.data.steps.TrailStep;
import dev.hollink.partytrails.runetime.TrailEventBus;
import java.awt.Color;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class StepEditorPanel extends JPanel implements FormHelper
{
	private final TrailEventBus clueEventBus;

	private final JComboBox<StepType> typeSelect = new JComboBox<>(StepType.values());

	private int stepNumber = 1;
	private StepEditor currentStepEditor;

	public StepEditorPanel(TrailEventBus clueEventBus, Consumer<StepEditorPanel> deleteCallback, Runnable updateCallback)
	{
		this.clueEventBus = clueEventBus;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		buildEditor(deleteCallback, updateCallback);

		typeSelect.addActionListener(e -> rebuildEditor(deleteCallback, updateCallback));
	}

	public void setStepNumber(int number)
	{
		this.stepNumber = number;
		setBorder(BorderFactory.createTitledBorder("Step " + number));
	}

	private void buildEditor(Consumer<StepEditorPanel> deleteCallback, Runnable updateCallback)
	{
		add(createRow(typeSelect));

		StepType type = (StepType) typeSelect.getSelectedItem();
		currentStepEditor = StepEditorFactory.create(type);
		currentStepEditor.setStepNumber(stepNumber);
		currentStepEditor.initForm();

		clueEventBus.register(currentStepEditor::onEvent);

		add(currentStepEditor);
		add(Box.createVerticalStrut(8));
		add(createButton("Delete step", (e) -> deleteCallback.accept(this)));

		revalidate();
		repaint();

		updateCallback.run();
	}

	private void rebuildEditor(Consumer<StepEditorPanel> deleteCallback, Runnable updateCallback)
	{
		if (currentStepEditor != null)
		{
			clueEventBus.unregister(currentStepEditor::onEvent);
		}
		removeAll();
		buildEditor(deleteCallback, updateCallback);
	}

	public List<StepEditorValidationError> getValidationErrors()
	{
		return currentStepEditor.validateUserInput();
	}

	public TrailStep toTrailStep()
	{
		return currentStepEditor.toTrailStep();
	}
}