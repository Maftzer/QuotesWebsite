const API_URL = "http://localhost:8080/api";

const select = document.getElementById("category-select");
const button = document.getElementById("random-btn");
const box = document.getElementById("quote-box");

async function loadCategories() {
  const response = await fetch(`${API_URL}/categories`);
  const categories = await response.json();

  const all = document.createElement("option");
  all.value = "";
  all.textContent = "All categories";
  select.appendChild(all);

  for (const category of categories) {
    const option = document.createElement("option");
    option.value = category.id;
    option.textContent = category.name;
    select.appendChild(option);
  }
}

async function loadRandomQuote() {
  const categoryId = select.value;
  const url = categoryId
    ? `${API_URL}/quotes/random?categoryId=${categoryId}`
    : `${API_URL}/quotes/random`;

  const response = await fetch(url);
  if (response.status === 404) {
    box.textContent = "No quotes found for this category.";
    return;
  }

  const quote = await response.json();
  box.textContent = `"${quote.text}" - ${quote.author ?? "Unknown"}`;
}

button.addEventListener("click", loadRandomQuote);
loadCategories();