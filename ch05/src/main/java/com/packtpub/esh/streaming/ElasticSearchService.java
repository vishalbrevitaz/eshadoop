package com.packtpub.esh.streaming;


import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.percolate.PercolateRequest;
import org.elasticsearch.action.percolate.PercolateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by vishalshukla on 11/07/15.
 */
public class ElasticSearchService {

    private static transient TransportClient client;

    static{
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", "eshadoopcluster").build();
        client = new TransportClient(settings);
        client.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
    }

    public ElasticSearchService(){

    }

    public List<String> percolate(Map map){
        List<String> ids = new ArrayList<String>();
        PercolateRequest request = new PercolateRequest();
        request.indices("es-storm");
        request.documentType("storm-tweets");
        ActionFuture<PercolateResponse> responseFuture = client.percolate(request.source(map));
        PercolateResponse response = responseFuture.actionGet();
        PercolateResponse.Match[] matches = response.getMatches();
        for(PercolateResponse.Match match: matches){
            ids.add(match.getId().toString());
        }
        return ids;
    }

}