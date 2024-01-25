<script lang="ts">
	import type { ProjectDtoSimple } from "$lib/models/project/ProjectDtoSimple";

	export let project: ProjectDtoSimple;
	export let username: string;

	let s = String(project.likes);
	let formatedLikes = () => {
		if (project.likes < 1000) {
			return String(project.likes);
		} else if (project.likes < 10000) {
			return `${s[0]},${s[1]}k`;
		} else if (project.likes < 100000) {
			return `${s[0]}${s[1]},${s[2]}k`;
		} else if (project.likes < 1000000) {
			return `${s[0]}${s[1]}${s[2]}k`;
		} else if (project.likes < 10000000) {
			return `${s[0]},${s[1]}${s[2]}M`;
		}
	};
</script>

<a href="/project/{project.id}" class="projectBox">
	<img class="lg" src="questionMark.png" alt="unknown programing language" />
	<div>
		<p class="projectName">{project.name}</p>
		{#if username != project.owner.username}
			<div class="moreInfo">
				<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"
					><path
						d="M12 12q-1.65 0-2.825-1.175T8 8q0-1.65 1.175-2.825T12 4q1.65 0 2.825 1.175T16 8q0 1.65-1.175 2.825T12 12Zm6 8H6q-.825 0-1.413-.588T4 18v-.8q0-.85.438-1.563T5.6 14.55q1.55-.775 3.15-1.163T12 13q1.65 0 3.25.388t3.15 1.162q.725.375 1.163 1.088T20 17.2v.8q0 .825-.588 1.413T18 20ZM6 18h12v-.8q0-.275-.138-.5t-.362-.35q-1.35-.675-2.725-1.012T12 15q-1.4 0-2.775.338T6.5 16.35q-.225.125-.363.35T6 17.2v.8Zm6-8q.825 0 1.413-.588T14 8q0-.825-.588-1.413T12 6q-.825 0-1.413.588T10 8q0 .825.588 1.413T12 10Zm0-2Zm0 10Z"
					/></svg
				>
				<p class="text">{project.owner.username}</p>
			</div>
		{:else}
			<div class="moreInfo">
				<img src="/icons/date.svg" alt="">
				<p class="text">{project.created.slice(0, 10)}</p>
			</div>
		{/if}
	</div>
	<div class="likes">
		<img src="/icons/hearthOutline.svg" alt="">
		<p style="margin-right: 8px;">{formatedLikes()}</p>
	</div>
</a>

<style lang="scss">
	.projectBox {
		width: 100%;
		height: 90px;
		display: flex;
		align-items: center;
		border: solid 1px #ffffff15;
		border-bottom: solid 1px rgba(77, 77, 77, 0.842);
		border-radius: 9px;
		background-color: #141313;

		.lg {
			width: 50px;
			height: 50;
			margin: 0 15px;
			margin-right: 25px;
			border-radius: 9px;
		}

		.projectName {
			margin: 0;
			margin-left: 1px;
			color: #fff;
			font-size: 18px;
			overflow-x: auto;
			font-family: 'Fira sans';
		}

		.moreInfo {
			margin-top: 2px;
			display: flex;
			align-items: center;

			img {
				margin-top: 1px;
				width: 20px;
				height: 20px;
				fill: var(--mainColor);
			}

			.text {
				margin: 0 8px;
				font-size: 15px;
				color: rgb(167, 167, 167);
			}
		}

		.likes {
			margin-left: auto;
			margin-right: 15px;
			display: flex;
			align-items: center;

			p {
				color: #ffffffd5;
			}

			img {
				width: 20px;
				height: 20px;
				margin-right: 6px;
				fill: var(--mainColor);
			}
		}
	}
</style>
