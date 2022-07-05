package com.freetube.JavaFreetube.Services;

import com.freetube.JavaFreetube.Repositories.SubscriptionsRepo;
import com.freetube.JavaFreetube.Services.Interfaces.ISubscriptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionsService implements ISubscriptionsService
{
    @Autowired
    SubscriptionsRepo repo;

}
