import csv
import pandas as pd

# Load the fetch.csv, visit.csv, and urls_nytimes.csv files
fetch_df = pd.read_csv('fetch_nytimes.csv')
visit_df = pd.read_csv('visit_nytimes.csv')
urls_df = pd.read_csv('urls_nytimes.csv')

# Filter fetch_df for only the URLs with a 200 status code
successful_urls = fetch_df[fetch_df['Status Code'] == 200]['URL']

# Filter visit_df for content types of successful URLs, and ensure we only include rows with valid content types
valid_visit_df = visit_df[visit_df['Content Type'].notna() & visit_df['Content Type'].str.strip().ne('')]
successful_content_types = valid_visit_df[valid_visit_df['URL'].isin(successful_urls)]['Content Type'].value_counts()

# Ensure certain content types always appear, even if their count is 0
required_content_types = {
    "text/html": 0,
    "image/gif": 0,
    "image/jpeg": 0,
    "image/png": 0,
    "application/pdf": 0
}

# Update the required content types with the actual counts
for content_type, count in successful_content_types.items():
    if content_type in required_content_types:
        required_content_types[content_type] = count
    else:
        required_content_types[content_type] = count

# Fetch statistics
fetch_stats = {
    'attempted': len(fetch_df),
    'succeeded': len(fetch_df[fetch_df['Status Code'].astype(str).str.startswith('2')]),
    'failed': len(fetch_df) - len(fetch_df[fetch_df['Status Code'].astype(str).str.startswith('2')])
}

# Outgoing URLs statistics
outgoing_urls = {
    'total': visit_df['# of OutLinks'].sum(),
    'unique': set(urls_df['URL']),
    'in_site': set(urls_df[urls_df['Status'] == 'OK']['URL']),
    'out_of_site': set(urls_df[urls_df['Status'] == 'N_OK']['URL'])
}

# Status code counts
status_counts = fetch_df['Status Code'].value_counts()

# File sizes
file_sizes = {'<1KB': 0, '1KB ~ <10KB': 0, '10KB ~ <100KB': 0, '100KB ~ <1MB': 0, '>=1MB': 0}
for size in visit_df['Size(Bytes)']:
    if size < 1024:
        file_sizes['<1KB'] += 1
    elif size < 10240:
        file_sizes['1KB ~ <10KB'] += 1
    elif size < 102400:
        file_sizes['10KB ~ <100KB'] += 1
    elif size < 1048576:
        file_sizes['100KB ~ <1MB'] += 1
    else:
        file_sizes['>=1MB'] += 1

def generate_report(fetch_stats, outgoing_urls, status_counts, file_sizes, required_content_types):
    report = []
    # Add the header information
    report.append("Name: GUK IL KIM")
    report.append("USC ID: 3020867072")
    report.append("News site crawled: nytimes.com")
    report.append("Number of threads: 15\n")

    report.append("Fetch Statistics")
    report.append("================")
    report.append(f"# fetches attempted: {fetch_stats['attempted']}")
    report.append(f"# fetches succeeded: {fetch_stats['succeeded']}")
    report.append(f"# fetches failed or aborted: {fetch_stats['failed']}\n")

    report.append("Outgoing URLs:")
    report.append("==============")
    report.append(f"Total URLs extracted: {outgoing_urls['total']}")
    report.append(f"# unique URLs extracted: {len(outgoing_urls['unique'])}")
    report.append(f"# unique URLs within News Site: {len(outgoing_urls['in_site'])}")
    report.append(f"# unique URLs outside News Site: {len(outgoing_urls['out_of_site'])}\n")

    report.append("Status Codes:")
    report.append("=============")
    report.append(f"200 OK: {status_counts.get(200, 0)}")
    report.append(f"301 Moved Permanently: {status_counts.get(301, 0)}")
    report.append(f"401 Unauthorized: {status_counts.get(401, 0)}")
    report.append(f"403 Forbidden: {status_counts.get(403, 0)}")
    report.append(f"404 Not Found: {status_counts.get(404, 0)}\n")

    report.append("File Sizes:")
    report.append("===========")
    for size_range, count in file_sizes.items():
        report.append(f"{size_range}: {count}")
    report.append("\n")

    report.append("Content Types:")
    report.append("==============")
    for content_type, count in required_content_types.items():
        report.append(f"{content_type}: {count}")

    return "\n".join(report)

def save_report(report, file_path):
    with open(file_path, mode='w', encoding='utf-8') as file:
        file.write(report)

# Generate the report
report = generate_report(fetch_stats, outgoing_urls, status_counts, file_sizes, required_content_types)

# Save the report
save_report(report, "CrawlReport_nytimes.txt")

print("Report generated successfully!")





