package com.freetube.JavaFreetube.Models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="subscriptions")
public class Subscriptions
{
    @Id
    public int id_channel;
    public int id_subscriber;
    public Date date_subscription;

    public Subscriptions() {
    }

    public Subscriptions(int id_channel, int id_subscriber, Date date_subscription) {
        this.id_channel = id_channel;
        this.id_subscriber = id_subscriber;
        this.date_subscription = date_subscription;
    }

    public int getId_channel() {
        return id_channel;
    }

    public void setId_channel(int id_channel) {
        this.id_channel = id_channel;
    }

    public int getId_subscriber() {
        return id_subscriber;
    }

    public void setId_subscriber(int id_subscriber) {
        this.id_subscriber = id_subscriber;
    }

    public Date getDate_subscription() {
        return date_subscription;
    }

    public void setDate_subscription(Date date_subscription) {
        this.date_subscription = date_subscription;
    }
}
