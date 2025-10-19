/**
 * Validação de Formulário
 * @author ray
 */
function validar() {
    let nome = frmContato.nome.value;
    let fone = frmContato.fone.value;
    let idade = frmContato.idade.value;
    
    if (nome === "" || nome.trim() === "") {
        alert('Preencha o campo Nome.');
        frmContato.nome.focus();
        return false;
    }
    
    if (nome.length > 100) {
        alert('O nome não pode ter mais de 100 caracteres.');
        frmContato.nome.focus();
        return false;
    }
    
    if (fone === "" || fone.trim() === "") {
        alert('Preencha o campo Telefone.');
        frmContato.fone.focus();
        return false;
    }
    
    if (fone.length > 16) {
        alert('O telefone não pode ter mais de 16 caracteres.');
        frmContato.fone.focus();
        return false;
    }
    
    let foneNumerico = fone.replace(/\D/g, '');
    if (foneNumerico.length < 8) {
        alert('O telefone deve ter pelo menos 8 dígitos.');
        frmContato.fone.focus();
        return false;
    }
    
    if (idade === "" || idade.trim() === "") {
        alert('Preencha o campo Idade.');
        frmContato.idade.focus();
        return false;
    }
    
    let idadeNum = parseInt(idade);
    
    if (isNaN(idadeNum)) {
        alert('A idade deve ser um número válido.');
        frmContato.idade.focus();
        return false;
    }
    
    if (idadeNum < 0) {
        alert('A idade não pode ser negativa.');
        frmContato.idade.focus();
        return false;
    }
    
    if (idadeNum > 999) {
        alert('A idade não pode ter mais de 3 dígitos.');
        frmContato.idade.focus();
        return false;
    }
    
    if (idade.length > 3) {
        alert('A idade não pode ter mais de 3 dígitos.');
        frmContato.idade.focus();
        return false;
    }
    
    document.forms["frmContato"].submit();
}