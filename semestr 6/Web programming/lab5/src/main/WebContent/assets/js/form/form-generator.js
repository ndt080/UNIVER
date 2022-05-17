const INPUT_STYLE_CLASS = 'form__input';
const LABEL_STYLE_CLASS = 'form__label';
const SUBMIT_BTN_STYLE_CLASS = 'form__button';
const GROUP_BLOCK__STYLE_CLASS = 'form__group';

function generateForm(targetFormId, formInputs, submitTitle) {
    const form = document.getElementById(targetFormId);

    const groupBlocks = formInputs.map((inputData, index) => {
        const inputId = createInputId(targetFormId, index);

        const label = createLabel(inputData, inputId);
        const input = createInput(inputData, inputId);
        label.append(input);

        return createGroupBlock(label);
    });

    groupBlocks.push(createGroupBlock(createSubmitButton(submitTitle)));

    form.append(...groupBlocks);
}

const createInputId = (formId, index) => `${formId}_input-${index}`;

const createInput = (inputData, inputId) => {
    const {type, name, placeholder, styleClasses, attributes} = inputData;
    const input = document.createElement('input');
    input.type = type || 'text';
    input.name = name;
    input.id = inputId;
    input.placeholder = placeholder || "Enter value...";
    input.classList.add(INPUT_STYLE_CLASS, ...styleClasses);

    attributes && attributes.forEach((value, key) => input.setAttribute(key, value));
    return input;
}

const createLabel = (inputData, inputId) => {
    const label = document.createElement('label');
    label.setAttribute('for', inputId);
    label.classList.add(LABEL_STYLE_CLASS);
    label.textContent = inputData?.label || 'Unknown field';
    return label;
}

const createSubmitButton = (btnTitle) => {
    const button = document.createElement('button');
    button.classList.add(SUBMIT_BTN_STYLE_CLASS);
    button.type = 'submit';
    button.textContent = btnTitle || 'Submit';
    return button;
}

const createGroupBlock = (...nodes) => {
    const block = document.createElement('div');
    block.classList.add(GROUP_BLOCK__STYLE_CLASS);
    block.append(...nodes);
    return block;
}

