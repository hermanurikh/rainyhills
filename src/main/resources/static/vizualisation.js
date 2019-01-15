function visualiseRain(groundArray, waterArray) {
    console.log(groundArray);
    console.log(waterArray);

    var waterVolume = document.getElementById('water-volume');

    for (var i = -1; i < 12; i++) {
        var newRow = document.createElement('div');
        newRow.className = "row";
        var insertedRow = insertAfter(newRow, waterVolume);
        for (var j = 0; j < 12; j++) {
            var newCol = document.createElement('div');

            var additionalClassName = "";
            if (j < groundArray.length && i !== -1) {
                //either ground or water
                if (i < groundArray[j]) {
                    additionalClassName = "ground"
                } else if (i < groundArray[j] + waterArray[j]) {
                    additionalClassName = "water";
                }
            }

            newCol.innerHTML = "&nbsp;";
            newCol.className = "col-sm-1 " + additionalClassName;
            newCol.setAttribute("align", "center");

            if (i === -1 && j < groundArray.length) {
                newCol.innerHTML = groundArray[j];
            }

            insertedRow.appendChild(newCol);
        }
    }
}

function insertAfter(newNode, referenceNode) {
    return referenceNode.parentNode.insertBefore(newNode, referenceNode.nextSibling);
}

function visualisationPossible(groundArray) {
    if (groundArray.length > 12) return false;

    for (var i = 0; i < groundArray.length; i++) {
        if (groundArray[i] < 0 || groundArray[i] > 11) {
            return false;
        }

    }
    return true;
}

function showNoVisualisationMessage() {
    alert('no visualisation for you');
}