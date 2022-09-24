function selecionaTipoItem() {
    var tipo = document.getElementById("tipoItem");
    if(tipo.value == 1){
        document.getElementById("tipoProduto").style.display = "block";
        document.getElementById("tipoServico").style.display = "none";
    } else if(tipo.value == 2) {
        document.getElementById("tipoProduto").style.display = "none";
        document.getElementById("tipoServico").style.display = "block";
    }
}