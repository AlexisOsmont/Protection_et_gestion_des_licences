  // Compute the edit distance between the two given strings
function getEditDistance(a, b) {
  if(a.length === 0) return b.length; 
  if(b.length === 0) return a.length; 

  var matrix = [];

  // increment along the first column of each row
  var i;
  for(i = 0; i <= b.length; i++){
    matrix[i] = [i];
  }

  // increment each column in the first row
  var j;
  for(j = 0; j <= a.length; j++){
    matrix[0][j] = j;
  }

  // Fill in the rest of the matrix
  for(i = 1; i <= b.length; i++){
    for(j = 1; j <= a.length; j++){
      if(b.charAt(i-1) == a.charAt(j-1)){
        matrix[i][j] = matrix[i-1][j-1];
      } else {
        matrix[i][j] = Math.min(matrix[i-1][j-1] + 1, // substitution
                                Math.min(matrix[i][j-1] + 1, // insertion
                                         matrix[i-1][j] + 1)); // deletion
      }
    }
  }

  return matrix[b.length][a.length];
};

document.addEventListener("DOMContentLoaded", function (event) {
	let input = document.getElementById("input");
	if (input != undefined)
	{
		// add callback to the input 
		input.oninput = function (event) {
			let str = event.target.value;
		
			let cards = [...document.getElementsByClassName("card")];
			cards = cards.map(x => x.parentElement);
			
			// sort the new array of cards depending on the 
			// levenstein distance between input strings and cards 
			// or just sort them alphabetically if the input string is empty
			let sortedCards = [];
			if (str.length > 0) {
		      sortedCards = [...cards].sort(function (a, b) {
					let str1 = a.getElementsByClassName("card-text")[0].innerText
							.replaceAll("\t", "")
							.replaceAll("\n", "");
					let str2 = b.getElementsByClassName("card-text")[0].innerText
							.replaceAll("\t", "")
							.replaceAll("\n", "");
					return getEditDistance(str1, str) > getEditDistance(str2, str);
				});
		  } else {
			 		sortedCards = [...cards].sort(function (a, b) {
					let str1 = a.getElementsByClassName("card-text")[0].innerText
							.replaceAll("\t", "")
							.replaceAll("\n", "");
					let str2 = b.getElementsByClassName("card-text")[0].innerText
							.replaceAll("\t", "")
							.replaceAll("\n", "");
					return str1 > str2;
				});
			}
			// clone the node to avoid DOM side effect when using replaceChild
			sortedCards = sortedCards.map(x => x.cloneNode(true));
		
			for (let i = 0; i < cards.length; ++i) {
				let toReplace   = cards[i];
				let parent      = toReplace.parentElement;
				let replaceWith = sortedCards[i];
				let textElement = replaceWith.getElementsByClassName("card-text")[0].textContent
					.replaceAll("\t", "")
					.replaceAll("\n", "");
		
				parent.replaceChild(replaceWith, toReplace);
			}
		}
		// now trigger manually an event to sort by default the page 
		input.dispatchEvent(new Event('input', { 'bubbles': true }));	
	}
});