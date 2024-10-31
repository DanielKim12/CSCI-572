# Homework Assignments - DSCI 552

## HW1: Sentiment Analysis and Text Classification

### Summary
This assignment focuses on performing sentiment analysis on a dataset of movie reviews. The main goal is to classify each review as positive or negative using various Natural Language Processing (NLP) and machine learning techniques.

### Instructions
1. **Data Preprocessing**: Perform text cleaning, tokenization, and conversion to lowercase.
2. **Feature Extraction**: Use TF-IDF to extract features from the reviews.
3. **Classification Models**: Train models like Naive Bayes, Support Vector Machine (SVM), and Logistic Regression to classify the sentiment.
4. **Evaluation**: Measure the performance using accuracy, precision, recall, and F1-score.

### Files
- `sentiment_analysis.py`: Python script for preprocessing, feature extraction, and model training.
- `README.md`: Detailed explanation of steps and outcomes.

---

## HW2: Web Crawling

### Summary
Develop a web crawler to fetch content from a list of URLs. The crawler should extract links from the content and recursively fetch new pages until the queue is empty. You can use any programming language and library, with suggestions provided for Java (Crawler4j), Python, JavaScript, and C++.

### Instructions
1. **Initialization**: Start with a single URL in a queue.
2. **Fetch Content**: Retrieve the page content, extract links, and add them to the queue.
3. **Recursion**: Repeat the process for each new URL.
4. **Statistics**: Compile statistics on the pages visited, including the number of pages crawled and the unique URLs encountered.

### Files
- `crawler.py` or `crawler.java`: Code for implementing the web crawler.
- `stats.txt`: Output file with statistics from the crawling session.

### Resources
- [Crawler4j Documentation](https://www.baeldung.com/crawler4j)
- [Python Web Scraping](https://www.scrapingbee.com/blog/crawling-python)
- [JavaScript Web Crawler](https://github.com/apify/crawlee)

---

## HW3: Inverted Index Creation

### Summary
In this assignment, you will create an inverted index for a collection of text files using MapReduce principles. The task involves building both unigram and bigram indexes.

### Instructions
1. **Unigram Index**: Create `unigram_index.txt` to map each word to the documents where it appears, along with its frequency.
2. **Bigram Index**: Generate `selected_bigram_index.txt` with specific bigrams (e.g., "computer science", "los angeles") and list their occurrences in `devdata`.
3. **Data Processing**: Clean the text, remove punctuation, and normalize to lowercase.
4. **Execution**: Use MapReduce principles to process the files and output the required indexes.

### Files
- `unigram_index.txt`: Inverted index file for unigrams.
- `selected_bigram_index.txt`: Inverted index file for specified bigrams.
- `README.md`: This file.

### Additional Resources
- [MapReduce Tutorial](https://hadoop.apache.org/docs/stable/hadoop-mapreduce-client/hadoop-mapreduce-client-core/MapReduceTutorial.html)
- [Repl.it Hadoop Word Counter Example](https://replit.com/@satychary/HadoopWordCounter)

---

### General Instructions
- Make sure to handle errors and edge cases in all code files.
- Submit screenshots of your output files and code execution results.
- Consult the Piazza forums or meet with TAs for further assistance.

---

Enjoy the assignments and happy coding!
