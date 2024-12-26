package com.demo.gitsearch.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GitItem {

    @JsonProperty("id")
    public int id;

    @JsonProperty("node_id")
    public String nodeId;

    @JsonProperty("name")
    public String name;

    @JsonProperty("full_name")
    public String fullName;

    @JsonProperty("private")
    public boolean isPrivate;

    @JsonProperty("owner")
    public GitOwner owner;

    @JsonProperty("html_url")
    public String htmlUrl;

    @JsonProperty("description")
    public String description;

    @JsonProperty("fork")
    public boolean fork;

    @JsonProperty("url")
    public String url;

    @JsonProperty("forks_url")
    public String forksUrl;

    @JsonProperty("keys_url")
    public String keysUrl;

    @JsonProperty("collaborators_url")
    public String collaboratorsUrl;

    @JsonProperty("teams_url")
    public String teamsUrl;

    @JsonProperty("hooks_url")
    public String hooksUrl;

    @JsonProperty("issue_events_url")
    public String issue_eventsUrl;

    @JsonProperty("events_url")
    public String eventsUrl;

    @JsonProperty("assignees_url")
    public String assigneesUrl;

    @JsonProperty("branches_url")
    public String branchesUrl;

    @JsonProperty("tags_url")
    public String tagsUrl;

    @JsonProperty("blobs_url")
    public String blobsUrl;

    @JsonProperty("git_tags_url")
    public String git_tagsUrl;

    @JsonProperty("git_refs_url")
    public String git_refsUrl;

    @JsonProperty("trees_url")
    public String treesUrl;

    @JsonProperty("statuses_url")
    public String statusesUrl;

    @JsonProperty("languages_url")
    public String languagesUrl;

    @JsonProperty("stargazers_url")
    public String stargazersUrl;

    @JsonProperty("contributors_url")
    public String contributorsUrl;

    @JsonProperty("subscribers_url")
    public String subscribersUrl;

    @JsonProperty("subscription_url")
    public String subscriptionUrl;

    @JsonProperty("commits_url")
    public String commitsUrl;

    @JsonProperty("git_commits_url")
    public String git_commitsUrl;

    @JsonProperty("comments_url")
    public String commentsUrl;

    @JsonProperty("issue_comment_url")
    public String issue_commentUrl;

    @JsonProperty("contents_url")
    public String contentsUrl;

    @JsonProperty("compare_url")
    public String compareUrl;

    @JsonProperty("merges_url")
    public String mergesUrl;

    @JsonProperty("archive_url")
    public String archiveUrl;

    @JsonProperty("downloads_url")
    public String downloadsUrl;

    @JsonProperty("issues_url")
    public String issuesUrl;

    @JsonProperty("pulls_url")
    public String pullsUrl;

    @JsonProperty("milestones_url")
    public String milestonesUrl;

    @JsonProperty("notifications_url")
    public String notificationsUrl;

    @JsonProperty("labels_url")
    public String labelsUrl;

    @JsonProperty("releases_url")
    public String releasesUrl;

    @JsonProperty("deployments_url")
    public String deploymentsUrl;

    @JsonProperty("created_at")
    public Date createdAt;

    @JsonProperty("updated_at")
    public Date updatedAt;

    @JsonProperty("pushed_at")
    public Date pushedAt;

    @JsonProperty("git_url")
    public String gitUrl;

    @JsonProperty("ssh_url")
    public String sshUrl;

    @JsonProperty("clone_url")
    public String cloneUrl;

    @JsonProperty("svn_url")
    public String svnUrl;

    @JsonProperty("homepage")
    public String homepage;

    @JsonProperty("size")
    public int size;

    @JsonProperty("stargazers_count")
    public int stargazersCount;

    @JsonProperty("watchers_count")
    public int watchersCount;

    @JsonProperty("language")
    public String language;

    @JsonProperty("has_issues")
    public boolean hasIssues;

    @JsonProperty("has_projects")
    public boolean hasProjects;

    @JsonProperty("has_downloads")
    public boolean hasDownloads;

    @JsonProperty("has_wiki")
    public boolean hasWiki;

    @JsonProperty("has_pages")
    public boolean hasPages;

    @JsonProperty("has_discussions")
    public boolean hasDiscussions;

    @JsonProperty("forks_count")
    public int forksCount;

    @JsonProperty("mirror_url")
    public String mirrorUrl;

    @JsonProperty("archived")
    public boolean archived;

    @JsonProperty("disabled")
    public boolean disabled;

    @JsonProperty("open_issues_count")
    public int openIssuesCount;

    @JsonProperty("license")
    public GitLicense license;

    @JsonProperty("allow_forking")
    public boolean allowForking;

    @JsonProperty("is_template")
    public boolean isTemplate;

    @JsonProperty("web_commit_signoff_required")
    public boolean webCommitSignoffRequired;

    @JsonProperty("topics")
    public List<String> topics;

    @JsonProperty("visibility")
    public String visibility;

    @JsonProperty("forks")
    public int forks;

    @JsonProperty("open_issues")
    public int openIssues;

    @JsonProperty("watchers")
    public int watchers;

    @JsonProperty("default_branch")
    public String defaultBranch;

    @JsonProperty("score")
    public double score;
}
