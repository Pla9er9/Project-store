<script lang="ts">
	import getColorOfLanguage from '$lib/colorOfLanguage'

	export let languages: Language[]

	type Language = {
		name: string
		filesCount: number
	}

	let sum = 0
	languages.forEach(e => {
		sum += e.filesCount	
	});
</script>

{#if languages.length > 0}
	<div id="languageBox">
		<div class="inline">
			{#each languages as lang}
				<div class="line" style="width: {lang.filesCount / sum * 100}%;background: {getColorOfLanguage(lang.name)}" />
			{/each}
		</div>
		<div class="inline" style="margin-left: 8px;">
			{#each languages as lang}
				<div class="circle" style="background: {getColorOfLanguage(lang.name)};" />
				<p>{lang.name} <span>{lang.filesCount / sum * 100}%</span></p>
			{/each}
		</div>
	</div>
{/if}

<style lang="scss">
	#languageBox {
		max-width: 750px;
		width: 90%;
		height: 70px;
		background: #111;
		border: solid 1px #ffffff11;
		border-radius: 9px;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: space-around;

		.inline {
			width: 94%;
			display: flex;
			align-items: center;
			overflow-x: auto;

			.line {
				margin-top: 16px;
				background-color: #fff;
				height: 6px;

				&:first-of-type {
					border-top-left-radius: 5px;
					border-bottom-left-radius: 5px;
				}

				&:last-of-type {
					border-top-right-radius: 5px;
					border-bottom-right-radius: 5px;
				}
			}

			.circle {
				width: 14px;
				height: 14px;
				border-radius: 50%;
				margin-right: 8px;
				background-color: #fff;
			}

			p {
				font-family: 'Fira sans';
				font-size: 13px;

				span {
					font-size: 12px;
					margin: 0 16px 0 2px;
					color: #d3d3d3;
				}
			}
		}
	}
</style>
