#  Distributed Network Load Balancer

A Java-based distributed system implementing Round Robin load balancing 
for fair request distribution across multiple server nodes.

## Overview

Built as part of Distributed Networks coursework, this system demonstrates 
load balancing principles by distributing incoming job requests across 
multiple nodes using the Round Robin algorithm. The system ensures fair 
resource utilization and prevents node overloading through sequential job 
assignment.

## Features

- **Round Robin Algorithm**: Sequential job distribution across available nodes
- **Node Registration**: Dynamic node registration via NodeManager
- **Job Tracking**: Real-time job status monitoring and completion reporting
- **Load Monitoring**: Detects uneven distribution and handles node failures
- **Multi-Threading**: Concurrent job processing across multiple nodes
- **User Authentication**: Login system with validation

## System Architecture

User → Server → Load Balancer → NodeManager → Nodes (1-5)

### Components:
- **Server**: Handles client connections and job requests (REG, JOB, FINISHED, STOP)
- **LoadBalancer**: Implements Round Robin distribution algorithm
- **NodeManager**: Registers and manages node availability
- **Nodes**: Process assigned jobs and report completion
- **User**: Authentication and job request interface

## Tech Stack

- **Language**: Java
- **Networking**: UDP (DatagramSocket, DatagramPacket)
- **IDE**: NetBeans
- **Architecture**: Distributed systems, Client-Server model

## Installation & Setup

### Prerequisites
- Java JDK 8 or higher
- NetBeans IDE (recommended)

### Running the System

1. **Start NodeManager** (registers 5 nodes):
# Port arguments for each node (increments by 1)
Run NodeManager with ports: 5000, 5001, 5002, 5003, 5004

##  Future Enhancements
- [ ] Persistent job queue (survive restarts)
- [ ] Weighted Round Robin (account for node capacity)
