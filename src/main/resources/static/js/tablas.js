let dietas = document.getElementById("tabla-dietas");
let rutinas = document.getElementById("tabla-rutinas");

let btnVerDietas = document.getElementById("ver-dietas")
btnVerDietas.addEventListener("click", function(){
    dietas.style.display = "block";
    rutinas.style.display = "none";
});

let btnVerRutinas = document.getElementById("ver-rutinas")
btnVerRutinas.addEventListener("click", function(){
    dietas.style.display = "none";
    rutinas.style.display = "block";
});
