<script lang="ts">
	export let tags: string[];

	let value: string = '';

	function addTag() {
		if (value.length <= 0 || tags.length > 10 || tags.includes(value)) {
			return;
		}
		tags = [...tags, value];
		value = '';
	}
</script>

<div id="tagInput">
	<div style="display: flex;align-items:center;border-bottom: solid 1px var(--lightBorder);">
		<input placeholder="Tag" bind:value maxlength="16" />
		<button on:click={addTag}>Add</button>
	</div>
	<div id="tags">
		{#each tags as tag}
			<button on:click={() => (tags = tags.filter((e) => e != tag))} class="tag">{tag}</button>
		{/each}
	</div>
	<small>{tags.length}/10</small>
</div>

<style lang="scss">
	#tagInput {
		width: 240px;
		height: 265px;
		margin: 10px 0;
		border: solid 1px var(--lightBorder);
        border-radius: 8px;
        background-color: inherit;
		display: flex;
		flex-direction: column;

		#tags {
			width: calc(100% - 12px);
			height: 60%;
			margin-top: auto;
			padding: 0 6px;
			display: flex;
			flex-wrap: wrap;
            align-content: start;
			overflow-y: auto;

			.tag {
				width: max-content;
				height: max-content;
				margin: 5px 4px;
				padding: 3px 8px;
				border: solid 1px var(--lightBorder);
				background-color: rgba(220 220 220 / 4.1%);
				border-radius: 15px;
				color: gainsboro;
				font-family: monospace;

				&:hover {
					border: solid 1px red;
					background-color: rgba(255 0 0 / 26.7%);
					color: red;
					cursor: pointer;
				}
			}
		}

		input {
			height: 36px;
			background-color: inherit;
			border: none;
			width: 100%;
			margin: 0;
			text-indent: 14px;
			outline: none;
			color: rgb(189 189 189);
		}

		button {
			width: 100px;
			margin: 0 8px;
			height: 22px;
			background-color: rgba(17 80 252 / 19.3%);
			color: rgb(0 89 255);
			font-size: 13px;
			font-family: 'Fira sans', sans-serif;
			border: solid 1px rgb(1 59 185);
			border-radius: 5px;
			cursor: pointer;
		}

		small {
			margin: auto 8px 4px auto;
			color: rgb(207 206 206);
			font-family: monospace;
		}
	}
</style>
