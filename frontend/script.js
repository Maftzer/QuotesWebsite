const API_URL = "http://localhost:8080/api";


async function getJson(path) {
  const response = await fetch(`${API_URL}${path}`);
  if (!response.ok) {
    throw new Error(`Request failed: ${response.status} ${response.url}`);
  }
  return response.json();
}

function quoteCountText(n) {
  return n === 1 ? "1 quote" : `${n} quotes`;
}

function authorHref(name) {
  return `quotes.html?${new URLSearchParams({ author: name })}`;
}

function categoryHref(category) {
  return `quotes.html?${new URLSearchParams({ categoryId: category.id, name: category.name })}`;
}


function createQuoteCard(quote) {
  const card = document.createElement("div");
  card.className = "quote-card";

  const text = document.createElement("p");
  text.className = "quote-text";
  text.textContent = `"${quote.text}"`;

  const author = document.createElement("p");
  author.className = "quote-author";
  if (quote.author) {
    const link = document.createElement("a");
    link.href = authorHref(quote.author);
    link.textContent = `- ${quote.author}`;
    author.appendChild(link);
  } else {
    author.textContent = "- Unknown";
  }

  card.append(text, author);
  return card;
}


function createListItem(label, count, href) {
  const li = document.createElement("li");
  const link = document.createElement("a");
  link.href = href;

  const name = document.createElement("span");
  name.textContent = label;

  const number = document.createElement("span");
  number.className = "count";
  number.textContent = quoteCountText(count);

  link.append(name, number);
  li.appendChild(link);
  return li;
}

async function fillList(path, listId, toItem) {
  const list = document.getElementById(listId);
  try {
    const items = await getJson(path);
    if (items.length === 0) {
      list.textContent = "Nothing here yet.";
      return;
    }
    for (const item of items) {
      list.appendChild(toItem(item));
    }
  } catch (error) {
    console.error(error);
    list.textContent = "Could not load data. Is the backend running?";
  }
}



async function loadQuoteOfTheDay() {
  const box = document.getElementById("quote-of-the-day");
  try {
    const response = await fetch(`${API_URL}/quotes/today`);
    if (response.status === 404) {
      box.textContent = "No quotes yet.";
      return;
    }
    if (!response.ok) throw new Error(`Request failed: ${response.status}`);
    box.appendChild(createQuoteCard(await response.json()));
  } catch (error) {
    console.error(error);
    box.textContent = "Could not load the quote of the day.";
  }
}

async function loadQuotesPage() {
  const params = new URLSearchParams(window.location.search);
  const title = document.getElementById("results-title");
  const results = document.getElementById("results");
  const apiParams = new URLSearchParams();

  if (params.get("search")) {
    apiParams.set("search", params.get("search"));
    title.textContent = `Results for "${params.get("search")}"`;
  } else if (params.get("categoryId")) {
    apiParams.set("categoryId", params.get("categoryId"));
    title.textContent = params.get("name") ?? "Category";
  } else if (params.get("author")) {
    apiParams.set("author", params.get("author"));
    title.textContent = params.get("author");
  } else {
    title.textContent = "All quotes";
  }

  try {
    const quotes = await getJson(`/quotes?${apiParams}`);
    if (quotes.length === 0) {
      results.textContent = "No quotes found.";
      return;
    }
    for (const quote of quotes) {
      results.appendChild(createQuoteCard(quote));
    }
  } catch (error) {
    console.error(error);
    results.textContent = "Could not load quotes. Is the backend running?";
  }
}


const page = document.body.dataset.page;

if (page === "home") {
  loadQuoteOfTheDay();
  fillList("/categories?limit=5", "popular-categories",
           c => createListItem(c.name, c.quoteCount, categoryHref(c)));
  fillList("/authors?limit=5", "popular-authors",
           a => createListItem(a.name, a.quoteCount, authorHref(a.name)));
} else if (page === "categories") {
  fillList("/categories", "all-categories",
           c => createListItem(c.name, c.quoteCount, categoryHref(c)));
} else if (page === "authors") {
  fillList("/authors", "all-authors",
           a => createListItem(a.name, a.quoteCount, authorHref(a.name)));
} else if (page === "quotes") {
  loadQuotesPage();
}