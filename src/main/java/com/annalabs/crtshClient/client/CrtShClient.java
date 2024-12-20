package com.annalabs.crtshClient.client;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


@Component
public class CrtShClient {

    private static final String CRT_SH_URL = "https://crt.sh/?q=";

    /**
     * Fetches subdomains for a given domain from crt.sh.
     *
     * @param domain The domain name to search.
     * @return A set of subdomains found on crt.sh.
     * @throws IOException If an error occurs during the request.
     */
    public Set<String> getSubdomains(String domain) throws IOException {
        String url = CRT_SH_URL + domain;
        Document document = Jsoup.connect(url).get();

        Set<String> subdomains = new HashSet<>();

        // Locate the main table containing certificate data
        Element mainTable = document.select("table").get(1);
        if (mainTable == null) {
            System.err.println("Main table not found on crt.sh for domain: " + domain);
            return subdomains;
        }

        // Iterate through rows of the main table
        Elements rows = mainTable.select("tr");
        for (Element row : rows) {
            Elements cells = row.select("td");

            // Skip rows without enough cells
            if (cells.size() < 5) continue;

            // Extract the subdomain from the 5th cell (Common Name column)
            String commonName = cells.get(5).text();
            if (commonName.endsWith(domain)) {
                subdomains.add(commonName);
            }
        }
        return subdomains;
    }
}
