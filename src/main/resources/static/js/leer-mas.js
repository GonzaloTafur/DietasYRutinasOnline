let descripciones = document.querySelectorAll(".descripcion")

descripciones.forEach(descripcion => {
    // Si "descripcion" tiene más de 150 caracteres, se recorta y se añade el boton "Leer más"
    if(descripcion.innerText.length > 150){
        let textoCompleto = descripcion.innerText;
        let textoRecortado = textoCompleto.substring(0, 170) + "... ";

        let leerMas = document.createElement("a");
        leerMas.href = "#";
        leerMas.innerText = "Leer más";

        leerMas.addEventListener("click", function() {
            descripcion.innerText = textoCompleto;
            leerMas.style.display = "none";
        });
        descripcion.innerText = textoRecortado;
        descripcion.appendChild(leerMas);
    }
});