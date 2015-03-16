_This document refers to 1.1.x and 1.2.x version of xCMIS._

# Introduction #
The CMIS standard defines a query language based on on a subset of the  SQL-92 grammar (ISO/IEC 9075: 1992 – Database Language SQL), with a few extensions to enhance its filtering capability for the CMIS data model, such as existential quantification for multi-valued property, full-text search, and folder membership.

# The CMIS Relational View #

The relational view of a CMIS repository consists of a collection of virtual tables that are defined on top of the CMIS data model.  A virtual table exists for every queryable object type (content type if you prefer) in the repository. Each row in these virtual tables correspond to an instance of the corresponding object type (or of one of its subtypes). A column exists for every property that the object type has.

# Query Capabilities #

| **Capability** | **Value** |
|:---------------|:----------|
| _capabilityQuery_ | `bothcombined` |
| _capabilityJoin_ | `none` |
| _capabilityPWCSearchable_ | `false` |
| _capabilityAllVersionsSearchable_ | `false` |


# Indexer #

For the Inmemory storage indexer already enabled by default without any configuration.


## Index atomicity and durability ##
### Write-ahead\_logging ###

To be able to provide index consistency and recoverability in the case of unexpected crashes or damages, XCMIS uses  [write-ahead logging](http://en.wikipedia.org/wiki/Write-ahead_logging) (WAL) technique. Write-Ahead Logging  is a standard approach to transaction logging. Briefly, WAL's central concept is that changes to data files (indexes) must be written only after those changes have been logged, that is, when log records describing the changes have been flushed to permanent storage. If we follow this procedure, we do not need to flush data pages to disk on every transaction commit, because we know that in the event of a crash we will be able to recover the index using the log: any changes that have not been applied to the data pages can be redone from the log records. (This is roll-forward recovery, also known as REDO.)

A major benefit of using WAL is a significantly reduced number of disk writes, because only the log file needs to be flushed to disk at the time of transaction commit, rather than every data file changed by the transaction.

### Recover uncommited transaction ###
When you start Indexer, it will check uncommitted transaction logs. If there are at least one log exists - recover process will be started.
Indexer will read all logs and extract added, updated and removed uuids into set. Then it will walk throw this set and check objects according to UUID.
If object exist - indexer will put into the added documents list, in other case  uuid will be added to removed documents list. After according to the list of added and removed documents changes will be applyed to the index.

### Initial index population ###
When you run the indexer checks the number of documents in the index. If there is no documents in the index or previous re-indexation wasn't successful then re-indexation of all content will be started. First step is cleaning old index data. Uncommited transaction logs and old persistent data is removed. This data is useless, because re-indexation of all content will be started. Then indexer walks throw all objects and make lucene document for each one. Then batches with less then 100 elements will be  saved to the index. After re-indexation, all  logs (WAL) will be removed, all data mentioned on this changes logs - already indexed.


**Note**: if administrator get exception with message "Can't remove reindex flag." it means that restore index was finished but file-flag was not removed ( see index directory, file named as "reindexProcessing"). You can manualy remove this file-flag, and avoid new reindex of repository on jcr start.