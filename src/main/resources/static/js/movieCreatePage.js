$(function(){
    const max = 4;
    const checkboxes = $('input[type="checkbox"]');

    checkboxes.change(function(){
        let current = checkboxes.filter(':checked').length;
        checkboxes.filter(':not(:checked)').prop('disabled', current >= max);
    });
});