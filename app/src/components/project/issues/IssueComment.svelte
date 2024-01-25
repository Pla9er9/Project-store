<script lang="ts">
	import Avatar from "$components/Avatar.svelte";
	import { PUBLIC_API_URL } from "$env/static/public";
	import type { IssueCommentDto } from "$lib/models/issue/IssueCommentDto";

	export let comment: IssueCommentDto;
</script>

<div class="line" />
<div class="issueComment">
	<div style="width: 100%;display:flex;align-items:center">
		<a href="/{comment.createdBy.username}">
			<Avatar size="40px" margin="10px 15px 10px 0" cursor="pointer" imageUrl="{PUBLIC_API_URL}/user/{comment.createdBy.username}/avatar" />
		</a>
		<p>{comment.createdBy.username} <br><small>{comment.created.slice(0, 10)}</small></p>
	</div>
	<p style="margin-left: 10px;">{comment.comment}</p>
</div>

<style lang="scss">
	.line {
		width: 1px;
		height: 50px;
		margin-left: 20px;
		border-left: solid 1px rgb(59, 78, 63);
	}

	.issueComment {
		max-width: 1000px;
		width: 90%;
		padding: 2px 15px;
		margin: 30px 0;
		outline: solid 1px rgba(105, 105, 105, 0.185);
		border-radius: 10px;
		display: flex;
		flex-wrap: wrap;
		align-items: center;

		small {
			color: rgb(168, 168, 168);
		}
	}
</style>
