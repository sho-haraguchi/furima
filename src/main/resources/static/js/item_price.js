window.addEventListener('DOMContentLoaded', () => {
  
  const priceInput = document.getElementById("item-price");
  const addTaxPrice = document.getElementById("add-tax-price");
  const profitInput = document.getElementById("profit");

  priceInput.addEventListener("input", () => {
    const inputValue = priceInput.value;

    if (/^[0-9]+$/.test(inputValue)) {
      const price = parseInt(inputValue, 10);

      const tax = Math.floor(price * 0.1);
      const profit = price - tax;

      addTaxPrice.textContent = tax.toLocaleString();
      profitInput.textContent = profit.toLocaleString();
      
    } else {
      addTaxPrice.textContent = "";
      profitInput.textContent = "";
    }
  });
});