/**
 * Copyright (C) 2011 Citrix Systems, Inc.  All rights reserved
 * 
 * This software is licensed under the GNU General Public License v3 or later.
 * 
 * It is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package com.cloud.network.dao;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;

import com.cloud.exception.UnsupportedServiceException;
import com.cloud.network.Network.Provider;
import com.cloud.network.Network.Service;
import com.cloud.network.NetworkServiceMapVO;
import com.cloud.utils.db.DB;
import com.cloud.utils.db.GenericDaoBase;
import com.cloud.utils.db.GenericSearchBuilder;
import com.cloud.utils.db.SearchBuilder;
import com.cloud.utils.db.SearchCriteria;
import com.cloud.utils.db.SearchCriteria.Func;

@Local(value=NetworkServiceMapDao.class) @DB(txn=false)
public class NetworkServiceMapDaoImpl extends GenericDaoBase<NetworkServiceMapVO, Long> implements NetworkServiceMapDao {
    final SearchBuilder<NetworkServiceMapVO> AllFieldsSearch;
    final SearchBuilder<NetworkServiceMapVO> MultipleServicesSearch;
    final GenericSearchBuilder<NetworkServiceMapVO, String> DistinctProvidersSearch;
    
    protected NetworkServiceMapDaoImpl() {
        super();
        AllFieldsSearch = createSearchBuilder();
        AllFieldsSearch.and("networkId", AllFieldsSearch.entity().getNetworkId(), SearchCriteria.Op.EQ);
        AllFieldsSearch.and("service", AllFieldsSearch.entity().getService(), SearchCriteria.Op.EQ);
        AllFieldsSearch.and("provider", AllFieldsSearch.entity().getProvider(), SearchCriteria.Op.EQ);
        AllFieldsSearch.done();
        
        MultipleServicesSearch = createSearchBuilder();
        MultipleServicesSearch.and("networkId", MultipleServicesSearch.entity().getNetworkId(), SearchCriteria.Op.EQ);
        MultipleServicesSearch.and("service", MultipleServicesSearch.entity().getService(), SearchCriteria.Op.IN);
        MultipleServicesSearch.and("provider", MultipleServicesSearch.entity().getProvider(), SearchCriteria.Op.EQ);
        MultipleServicesSearch.done();
        
        DistinctProvidersSearch = createSearchBuilder(String.class);
        DistinctProvidersSearch.and("networkId", DistinctProvidersSearch.entity().getNetworkId(), SearchCriteria.Op.EQ);
        DistinctProvidersSearch.and("provider", DistinctProvidersSearch.entity().getProvider(), SearchCriteria.Op.EQ);
        DistinctProvidersSearch.select(null, Func.DISTINCT, DistinctProvidersSearch.entity().getProvider());
        DistinctProvidersSearch.done();
        
    }
    
    @Override
    public boolean areServicesSupportedInNetwork(long networkId, Service... services) {
        SearchCriteria<NetworkServiceMapVO> sc = MultipleServicesSearch.create();
        sc.setParameters("networkId", networkId);
        
        if (services != null) {
            String[] servicesStr = new String[services.length];
            
            int i = 0;
            for (Service service : services) {
                servicesStr[i] = service.getName();
                i++;
            }
            
            sc.setParameters("service", (Object[])servicesStr);
        }
        
        List<NetworkServiceMapVO> networkServices = listBy(sc);
        
        if (services != null) {
            if (networkServices.size() == services.length) {
                return true;
            }
        } else if (!networkServices.isEmpty()) {
            return true;
        }
        
        return false;
    }
    
    @Override
    public boolean canProviderSupportServiceInNetwork(long networkId, Service service, Provider provider) {
        SearchCriteria<NetworkServiceMapVO> sc = AllFieldsSearch.create();
        sc.setParameters("networkId", networkId);
        sc.setParameters("service", service.getName());
        sc.setParameters("provider", provider.getName());
        if (findOneBy(sc) != null) {
            return true;
        } else {
            return false;
        }
    }
    
    protected List<String> getServicesForProviderInNetwork(long networkId, Provider provider) {
        List<String> services = new ArrayList<String>();
        SearchCriteria<NetworkServiceMapVO> sc = AllFieldsSearch.create();
        sc.setParameters("networkId", networkId);
        sc.setParameters("provider", provider.getName());
        List<NetworkServiceMapVO> map = listBy(sc);
        for (NetworkServiceMapVO instance : map) {
            services.add(instance.getService());
        }
        
        return services;
    }
    
    @Override
    public String getProviderForServiceInNetwork(long networkId, Service service) {
        SearchCriteria<NetworkServiceMapVO> sc = AllFieldsSearch.create();
        sc.setParameters("networkId", networkId);
        sc.setParameters("service", service.getName());
        NetworkServiceMapVO ntwkSvc = findOneBy(sc);
        if (ntwkSvc == null) {
            throw new UnsupportedServiceException("Service " + service + " is not supported in the network id=" + networkId);
        }
        
        return ntwkSvc.getProvider();
    }
 
    @Override
    public List<NetworkServiceMapVO> getServicesInNetwork(long networkId) {
        SearchCriteria<NetworkServiceMapVO> sc = AllFieldsSearch.create();
        sc.setParameters("networkId", networkId);
        return listBy(sc);
    }
    
    @Override
    public void deleteByNetworkId(long networkId) {
        SearchCriteria<NetworkServiceMapVO> sc = AllFieldsSearch.create();
        sc.setParameters("networkId", networkId);
        remove(sc);
    }
    
    @Override
    public List<String> getDistinctProviders(long networkId) {
        SearchCriteria<String> sc = DistinctProvidersSearch.create();
        sc.setParameters("networkId", networkId);
        List<String> results = customSearch(sc, null);
        return results;
    }
    
    @Override
    public String isProviderForNetwork(long networkId, Provider provider) {
    	SearchCriteria<String> sc = DistinctProvidersSearch.create();
        sc.setParameters("networkId", networkId);
        sc.setParameters("provider", provider.getName());
        List<String> results = customSearch(sc, null);
        if (results.isEmpty()) {
        	return null;
        } else {
        	return results.get(0);
        }
    }
    
}
