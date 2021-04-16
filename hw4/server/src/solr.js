var solr = require('solr-client');

var client = solr.createClient({
    core: 'myexample'
});

var limit = 10;

function parseAttribute(value) {
    if (!value) {
        return 'N/A';
    }
    if (!Array.isArray(value)) {
        return value;
    }
    if (value.length === 0 || !value) {
        return 'N/A';
    }
    return value[0];
}

function buildData(object) {
    var response = object.response;

    var total = response.numFound;
    var start = Math.min(1, total);
    var end = Math.min(limit, total);

    var documents = [];
    response.docs.forEach(function (doc) {
        documents.push({
            id: parseAttribute(doc.id),
            description: parseAttribute(doc.description),
            url: parseAttribute(doc.og_url),
            title: parseAttribute(doc.title)
        })
    });

    return {
        start,
        end,
        total,
        documents
    }
}

function buildSolrQuery(query, type, callback) {
    let solrQuery;
    switch (type) {
        case 'lucene':
            solrQuery = client.createQuery()
                .q(query)
                .start(0)
                .rows(limit);
            break;
        case 'pagerank':
            solrQuery = client.createQuery()
                .q(query)
                .sort({ pageRankFile: 'desc' })
                .start(0)
                .rows(limit);
            break;
        default:
            callback(new Error('Invalid query type'), null);
            return
    }
    callback(null, solrQuery);
}

function runSolrQuery(solrQuery, callback) {
    client.search(solrQuery, function (error, object) {
        if (error) {
            console.log(error);
            callback(error, null);
        } else {
            var data = buildData(object);
            callback(null, data);
        }
    });
}

function search(query, type, callback) {
    buildSolrQuery(query, type, function (error, solrQuery) {
        if (error) {
            console.log(error);
            callback(error, null);
        } else {
            runSolrQuery(solrQuery, callback);
        }
    });
}

module.exports = {
    search
};