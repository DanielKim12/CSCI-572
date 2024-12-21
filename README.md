# Homework Assignments - DSCI 552

| Homework | Summary | Instructions | Files | Additional Resources |
|----------|---------|--------------|-------|-----------------------|
| **HW1**: Sentiment Analysis and Text Classification | Perform sentiment analysis on movie reviews to classify each review as positive or negative using NLP and machine learning techniques. | 1. **Data Preprocessing**: Clean, tokenize, and lowercase text.<br>2. **Feature Extraction**: Use TF-IDF.<br>3. **Classification Models**: Train Naive Bayes, SVM, and Logistic Regression models.<br>4. **Evaluation**: Measure accuracy, precision, recall, and F1-score. | - `sentiment_analysis.py`: Python script for analysis.<br>- `README.md`: Details on steps and outcomes. | |
| **HW2**: Web Crawling | Develop a web crawler to fetch content from URLs, extract links, and recursively crawl new pages until the queue is empty. | 1. **Initialization**: Start with a URL queue.<br>2. **Fetch Content**: Retrieve page content, extract links, and add them to the queue.<br>3. **Recursion**: Process each new URL.<br>4. **Statistics**: Record the number of pages crawled and unique URLs encountered. | - `crawler.py` or `crawler.java`: Code for the web crawler.<br>- `stats.txt`: Crawling session statistics. | - [Crawler4j Documentation](https://www.baeldung.com/crawler4j)<br>- [Python Web Scraping](https://www.scrapingbee.com/blog/crawling-python)<br>- [JavaScript Web Crawler](https://github.com/apify/crawlee) |
| **HW3**: Inverted Index Creation | Create an inverted index for text files using MapReduce principles, including both unigram and bigram indexes. | 1. **Unigram Index**: Create `unigram_index.txt` mapping each word to documents and frequencies.<br>2. **Bigram Index**: Generate `selected_bigram_index.txt` for specific bigrams (e.g., "computer science").<br>3. **Data Processing**: Clean text, remove punctuation, and normalize.<br>4. **Execution**: Use MapReduce principles to process files. | - `unigram_index.txt`: Unigram index file.<br>- `selected_bigram_index.txt`: Specific bigrams index.<br>- `README.md`: Overview and guidelines. | - [MapReduce Tutorial](https://hadoop.apache.org/docs/stable/hadoop-mapreduce-client/hadoop-mapreduce-client-core/MapReduceTutorial.html)<br>- [Repl.it Hadoop Example](https://replit.com/@satychary/HadoopWordCounter) |
| **HW4**       | LLMs, Vectors, and RAG                  | Use Weaviate for vector-based similarity search and Lightning.ai to run RAG on GPU-accelerated cloud environments. | - `docker-compose.yml`: Config file to run Weaviate with text2vec transformer.<br>- `weave-loadData.py`: Script to load data into the vector DB.<br>- `serveit.py`: Web server script to serve JSON data.<br>- `weave-doQuery.sh`: Script to query vectorized data. | - [Weaviate Documentation](https://weaviate.io/)<br>- [HuggingFace RAG](https://huggingface.co/spaces) |
| **HW5**       | Local Running of LLMs                   | Install and invoke local LLMs for tasks like chat and code generation using tools like Ollama and Streamlit. | - `invokeMultipleLLMs.py`: Streamlit app to send prompts to multiple LLMs.<br>- Screenshots of outputs. | - [Ollama Documentation](https://ollama.com/)<br>- [Streamlit Documentation](https://streamlit.io/) |

---

### General Instructions
- Ensure error handling and edge case management in all code files.
- Submit screenshots of output files and code execution results.
- Refer to Piazza or consult TAs for additional assistance.

---

