const REGEXP_EMAIL = `^(([^<>()[\\]\\\\.,;:\\s@\\"]+(\\.[^<>()[\\]\\\\.,;:\\s@\\"]+)*)|(\\".+\\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$`;
const REGEXP_NUMBERS = `^\\d$`;

const INPUT_ERR_STYLE_CLASS = 'form__input--error';
const ERROR_BLOCK_STYLE_CLASS = 'form__error_message';

function initInputValidation(formName) {
    const form = document.querySelector(`[data-form='${formName}']`);
    if (!form) return;

    const inputsState = new Map();
    setSubmit(form, true);

    const inputs = form.querySelectorAll('[data-validation=true]');
    inputs.forEach((input) => {
        inputsState.set(input, false);
        input.parentNode?.insertAdjacentElement('afterend', createErrorBlock(input));
        input.addEventListener('input', (event) => onInputChange(event, form, inputsState));
        input.addEventListener('blur', (event) => onInputChange(event, form, inputsState));
    });
}

const onInputChange = (event, form, inputsState) => {
    const input = event.target;
    const value = input.value;
    const validationParams = input.attributes.getNamedItem('data-validation-params').value.split(';');
    const err = checkValidation(form, value, validationParams);
    inputsState.set(input, !err);

    if(checkFormValidity(inputsState)) setSubmit(form, false);

    err && createErrorMessage(input, err);
    !err && removeErrorMessage(input);
}

const checkValidation = (form, value, params) => {
    let err;
    params.forEach((param) => {
        const type = param.split('_');
        switch (true) {
            case type[0] === 'email' && !new RegExp(REGEXP_EMAIL, "g").test(value):
                setSubmit(form, true);
               return (err = 'Invalid email');
            case type[0] === 'numbers' && !new RegExp(REGEXP_NUMBERS, "g").test(value):
                setSubmit(form, true);
                return (err = 'Invalid number value');
            case type[0] === 'max' && value.length > Number(type[1]):
                setSubmit(form, true);
                return (err = 'Invalid max value length');
            case type[0] === 'min' && value.length < Number(type[1]):
                setSubmit(form, true);
                return (err = 'Invalid min value length');
            case type[0] === 'require' && !value:
                setSubmit(form, true);
                return (err = 'Required field');
        }
    });
    return err;
}

const checkFormValidity = (inputsState) => {
    let isValid = true;
    inputsState.forEach(input => (!input && (isValid = false)));
    return isValid;
}

const createErrorBlock = (input) => {
    const errorBlock = document.createElement('div');
    errorBlock.classList.add(ERROR_BLOCK_STYLE_CLASS);
    errorBlock.setAttribute('for', input.id);
    return errorBlock;
}

const getErrorBlock = (input) => {
    const elements = input.parentElement.parentNode.querySelectorAll(`[for=${input.id}]`);
    return elements.length >= 2 ? elements[elements.length - 1] : elements[0];
};

const createErrorMessage = (input, err) => {
    const errorBlock = getErrorBlock(input);
    errorBlock.textContent = err;
    input.classList.add(INPUT_ERR_STYLE_CLASS);
}

const removeErrorMessage = (input) => {
    const errorBlock = getErrorBlock(input);
    errorBlock.textContent = '';
    input.classList.remove(INPUT_ERR_STYLE_CLASS);
}

const setSubmit = (form, isDisabled) => {
    const submitBtn = form.querySelector(`[type='submit']`);
    submitBtn.disabled = isDisabled;
}


